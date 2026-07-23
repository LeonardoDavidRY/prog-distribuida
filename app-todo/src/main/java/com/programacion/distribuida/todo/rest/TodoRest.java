package com.programacion.distribuida.todo.rest;

import com.programacion.distribuida.todo.clients.TodoSourceRestClient;
import com.programacion.distribuida.todo.clients.UserRestClient;
import com.programacion.distribuida.todo.db.Todo;
import com.programacion.distribuida.todo.dtos.TodoDto;
import com.programacion.distribuida.todo.repo.TodoRepository;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ApplicationScoped
public class TodoRest {

    final TodoRepository todoRepository;
    final UserRestClient userClient;
    final TodoSourceRestClient todoSourceClient;

    @Inject
    public TodoRest(TodoRepository todoRepository,
                     @RestClient UserRestClient userClient,
                     @RestClient TodoSourceRestClient todoSourceClient) {
        this.todoRepository = todoRepository;
        this.userClient = userClient;
        this.todoSourceClient = todoSourceClient;
    }

    // GET /todos -> lista plana de lo que hay en la DB local (solo id/title/completed,
    // sin usuario: la tabla todos no guarda a quién pertenece cada TODO)
    @GET
    public List<Todo> findAll() {
        return todoRepository.listAll();
    }

    // GET /todos/user/{userId} -> lo que consume la app WEB.
    // La DB local NO guarda el userId dueño de cada TODO, así que para saber cuáles
    // TODOs son de este usuario se consulta en vivo al sistema externo
    // (https://jsonplaceholder.typicode.com/todos?userId={userId}); el title/completed
    // que se muestra viene de la DB local (fuente de verdad para ediciones propias),
    // y si un TODO todavía no existe localmente se guarda en ese momento.
    @GET
    @Path("/user/{userId}")
    @WithSpan("TodoRest.findByUser")
    public List<TodoDto> findByUser(@SpanAttribute("userId") @PathParam("userId") Long userId) {
        var user = userClient.findUser(userId);
        var externalTodos = todoSourceClient.findByUserId(userId);

        return externalTodos.stream()
                .map(source -> {
                    var todo = todoRepository.findByIdOptional(source.getId())
                            .orElseGet(() -> {
                                Todo nuevo = new Todo();
                                nuevo.setId(source.getId());
                                nuevo.setTitle(source.getTitle());
                                nuevo.setCompleted(source.getCompleted());
                                todoRepository.persist(nuevo);
                                return nuevo;
                            });

                    return TodoDto.builder()
                            .id(todo.getId())
                            .username(user.getUsername())
                            .name(user.getName())
                            .title(todo.getTitle())
                            .completed(todo.getCompleted())
                            .build();
                })
                .toList();
    }

    @GET
    @Path("/{id}")
    @WithSpan("TodoRest.findById")
    public Response findById(@SpanAttribute("id") @PathParam("id") Long id) {
        return todoRepository.findByIdOptional(id)
                .map(todo -> {
                    Span.current().setAttribute("todo.title", todo.getTitle());
                    return TodoDto.builder()
                            .id(todo.getId())
                            .title(todo.getTitle())
                            .completed(todo.getCompleted())
                            .build();
                })
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    public Response create(Todo todo) {
        todoRepository.persist(todo);
        var uri = UriBuilder.fromUri("/todos/{id}").build(todo.getId());
        return Response.created(uri).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Todo todo) {
        return todoRepository.findByIdOptional(id)
                .map(existing -> {
                    existing.setTitle(todo.getTitle());
                    existing.setCompleted(todo.getCompleted());
                    return Response.ok().build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        todoRepository.deleteById(id);
        return Response.ok().build();
    }
}
