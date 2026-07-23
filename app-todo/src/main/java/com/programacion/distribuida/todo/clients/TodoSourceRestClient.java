package com.programacion.distribuida.todo.clients;

import com.programacion.distribuida.todo.dtos.TodoSourceDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

// Cliente hacia el documento JSON fuente de la actividad:
// https://jsonplaceholder.typicode.com/todos
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "TodoSourceRestClient")
public interface TodoSourceRestClient {

    // Usado una única vez al arrancar (ver TodoDataLoader) para poblar la DB local
    // con id/title/completed, que es lo único que "solo almacena información de TODOs"
    // según el enunciado guarda.
    @GET
    @Path("/todos")
    @Timeout(5000)
    List<TodoSourceDto> findAll();

    // Usado en cada consulta "TODOs de un usuario": como la DB local NO guarda el
    // userId dueño de cada TODO, se resuelve en vivo filtrando en el sistema externo.
    @GET
    @Path("/todos")
    @Timeout(3000)
    List<TodoSourceDto> findByUserId(@QueryParam("userId") Long userId);
}
