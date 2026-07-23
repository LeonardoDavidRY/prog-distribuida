package com.programacion.distribuida.todo.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

// Conectividad a la DB es una preocupación de READINESS, no de liveness (igual que
// AuthorServiceHealthCheck/BookServiceHealthCheck): un problema de DB no debe hacer
// que Kubernetes/Docker reinicien el contenedor, solo debe sacarlo de la rotación.
@Readiness
public class TodoServiceHealthCheck implements HealthCheck {

    @Inject
    EntityManager entityManager;

    @Override
    public HealthCheckResponse call() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return HealthCheckResponse.named("TodoService-DB").up().build();
        } catch (Exception e) {
            return HealthCheckResponse.named("TodoService-DB").down()
                    .withData("error", e.getMessage())
                    .build();
        }
    }
}
