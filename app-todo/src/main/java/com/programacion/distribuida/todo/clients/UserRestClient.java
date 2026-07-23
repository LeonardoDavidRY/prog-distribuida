package com.programacion.distribuida.todo.clients;

import com.programacion.distribuida.todo.dtos.UserDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

// Cliente REST hacia el sistema externo de usuarios (jsonplaceholder).
// A diferencia de AuthorRestClient (app-books), este NO usa "stork://" porque no es
// un microservicio propio registrado en Consul, sino un servicio externo con URL fija
// (configurada en microprofile-config.properties: UserRestClient/mp-rest/url).
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "UserRestClient")
public interface UserRestClient {

    @GET
    @Path("/users/{userId}")
    @Timeout(3000)
    @Fallback(fallbackMethod = "findUserFallback")
    UserDto findUser(@PathParam("userId") Long userId);

    default UserDto findUserFallback(Long userId) {
        return UserDto.builder()
                .id(userId != null ? userId.intValue() : 0)
                .name("no-disponible")
                .username("no-disponible")
                .build();
    }
}
