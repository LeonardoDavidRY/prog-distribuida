package com.programacion.distribuida.authors.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

// DB connectivity is a READINESS concern, not liveness: if the DB is briefly
// unreachable, restarting this container (what a failed liveness probe triggers)
// will not fix anything. Readiness just takes it out of the load-balancer/consul
// rotation until the DB answers again.
@Readiness
public class AuthorServiceHealthCheck implements HealthCheck {

    @Inject
    EntityManager entityManager;

    @Override
    public HealthCheckResponse call() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return HealthCheckResponse.named("AuthorService-DB").up().build();
        } catch (Exception e) {
            return HealthCheckResponse.named("AuthorService-DB").down()
                    .withData("error", e.getMessage())
                    .build();
        }
    }
}
