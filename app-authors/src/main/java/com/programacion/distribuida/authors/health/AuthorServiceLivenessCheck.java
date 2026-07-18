package com.programacion.distribuida.authors.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

// Liveness only proves the JVM/HTTP layer is up and able to respond.
// It must NOT depend on external resources (DB, Consul, other services):
// those go in @Readiness checks instead, otherwise a slow DB would cause
// Docker/Kubernetes to keep killing and restarting a perfectly healthy process.
@Liveness
public class AuthorServiceLivenessCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("AuthorService-Live").up().build();
    }
}
