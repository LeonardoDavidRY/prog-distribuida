package com.programacion.distribuida.books.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

// DB connectivity is a READINESS concern, not liveness (see AuthorServiceHealthCheck
// for the same reasoning): a DB outage shouldn't cause Docker/Kubernetes to restart
// this container, it should just stop routing traffic to it until the DB recovers.
@Readiness
public class BookServiceHealthCheck implements HealthCheck {

    @Inject
    EntityManager entityManager;

    @Override
    public HealthCheckResponse call() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return HealthCheckResponse.named("BookService-DB").up().build();
        } catch (Exception e) {
            return HealthCheckResponse.named("BookService-DB").down()
                    .withData("error", e.getMessage())
                    .build();
        }
    }
}
