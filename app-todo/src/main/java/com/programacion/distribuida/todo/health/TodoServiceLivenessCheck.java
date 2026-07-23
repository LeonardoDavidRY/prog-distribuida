package com.programacion.distribuida.todo.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

// Liveness solo prueba que la capa JVM/HTTP responde. Dependencias externas
// (DB, Users API) se verifican en @Readiness (ver TodoServiceHealthCheck y
// TodoServiceReadinessCheck).
@Liveness
public class TodoServiceLivenessCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("TodoService-Live").up().build();
    }
}
