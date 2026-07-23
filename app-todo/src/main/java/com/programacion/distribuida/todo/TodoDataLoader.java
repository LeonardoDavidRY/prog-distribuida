package com.programacion.distribuida.todo;

import com.programacion.distribuida.todo.clients.TodoSourceRestClient;
import com.programacion.distribuida.todo.db.Todo;
import com.programacion.distribuida.todo.repo.TodoRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

// Puebla la DB local consumiendo https://jsonplaceholder.typicode.com/todos, tal como
// pide el enunciado ("Considere el siguiente documento JSON ... el cual contiene una
// lista de TO-Dos"). No se usan INSERTs en Flyway: Flyway solo crea la tabla
// (DB configurada automáticamente) y esta clase carga los datos reales al arrancar,
// una sola vez (si la tabla ya tiene datos, no se vuelve a cargar).
public class TodoDataLoader {

    @Inject
    TodoRepository todoRepository;

    @Inject
    @RestClient
    TodoSourceRestClient todoSourceRestClient;

    public void init(@Observes StartupEvent event) {
        System.out.println("Todo - DataLoader: init");
        try {
            if (todoRepository.count() > 0) {
                System.out.println("Todo - DataLoader: la tabla todos ya tiene datos, se omite la carga inicial");
                return;
            }

            System.out.println("Todo - DataLoader: consultando https://jsonplaceholder.typicode.com/todos");
            var externalTodos = todoSourceRestClient.findAll();

            var todos = externalTodos.stream()
                    .map(it -> {
                        Todo todo = new Todo();
                        todo.setId(it.getId());
                        todo.setTitle(it.getTitle());
                        todo.setCompleted(it.getCompleted());
                        return todo;
                    })
                    .toList();

            todoRepository.persist(todos);
            System.out.println("Todo - DataLoader: " + todos.size() + " TODOs cargados desde el sistema externo");
        } catch (Exception e) {
            System.out.println("Todo - DataLoader: fallo al cargar TODOs iniciales: " + e.getMessage());
        }
    }
}
