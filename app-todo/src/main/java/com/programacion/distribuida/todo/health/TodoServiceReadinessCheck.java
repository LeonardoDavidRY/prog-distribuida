package com.programacion.distribuida.todo.health;

import com.programacion.distribuida.todo.clients.UserRestClient;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

// Igual que BookServiceReadinessCheck comprueba la disponibilidad de la Authors API,
// aquí se comprueba la disponibilidad del sistema externo de USUARIOS.
@Readiness
public class TodoServiceReadinessCheck implements HealthCheck {

    @Inject
    @RestClient
    UserRestClient userRestClient;

    @Override
    public HealthCheckResponse call() {
        try {
            userRestClient.findUser(1L);
            return HealthCheckResponse.named("TodoService-UsersAPI").up().build();
        } catch (Exception e) {
            return HealthCheckResponse.named("TodoService-UsersAPI").down()
                    .withData("error", e.getClass().getSimpleName() + ": " + e.getMessage())
                    .build();
        }
    }
}
