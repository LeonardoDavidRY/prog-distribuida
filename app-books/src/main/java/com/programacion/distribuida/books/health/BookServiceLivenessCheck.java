package com.programacion.distribuida.books.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

// Liveness only proves the JVM/HTTP layer is up. External dependencies (DB,
// Authors API) are checked under @Readiness instead (see BookServiceHealthCheck
// and BookServiceReadinessCheck).
@Liveness
public class BookServiceLivenessCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("BookService-Live").up().build();
    }
}
