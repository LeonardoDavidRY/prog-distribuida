package com.programacion.distribuida.todo;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.CheckOptions;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;
import java.util.List;

// Registro/baja del servicio en Consul (Registro + habilita balanceo de carga dinámico
// vía Stork para quien consuma app-todo, igual que AuthorsLifeCycle/BooksLifeCycle).
public class TodoLifeCycle {

    @Inject
    @ConfigProperty(name = "consul.host", defaultValue = "127.0.0.1")
    String consulHost;
    @Inject
    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    Integer consulPort;

    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8082")
    Integer appPort;

    String serviceId;
    String ipAddress;

    public void init(@Observes StartupEvent event, Vertx vertx) {

        System.out.println("Todo - LifeCycle: init");
        try {
            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulHost)
                    .setPort(consulPort);

            ConsulClient client = ConsulClient.create(vertx, options);

            ipAddress = InetAddress.getLocalHost().getHostAddress();

            serviceId = "app-todo-%s:%d".formatted(ipAddress, appPort);

            var urlCheck = "http://%s:%d/ping".formatted(ipAddress, appPort);

            CheckOptions checkOptions = new CheckOptions()
                    .setHttp(urlCheck)
                    .setInterval("10s")
                    .setDeregisterAfter("10s");

            var tags = List.of(
                    "traefik.enable=true",
                    "traefik.http.routers.router-app-todo.rule=PathPrefix(`/app-todo`)",
                    "traefik.http.routers.router-app-todo.middlewares=middleware-todo",
                    "traefik.http.middlewares.middleware-todo.stripprefix.prefixes=/app-todo"
            );

            ServiceOptions serviceOptions = new ServiceOptions()
                    .setName("app-todo")
                    .setId(serviceId)
                    .setAddress(ipAddress)
                    .setPort(appPort)
                    .setCheckOptions(checkOptions)
                    .setTags(tags);

            client.registerService(serviceOptions)
                    .onSuccess(it -> System.out.println("Todo service registered in Consul with ID: " + serviceId))
                    .onFailure(it -> {
                        System.out.println("Failed to register Todo service in Consul: " + it.getMessage());
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy(@Observes ShutdownEvent event, Vertx vertx) {
        System.out.println("Todo - LifeCycle: destroy");

        ConsulClientOptions options = new ConsulClientOptions()
                .setHost(consulHost)
                .setPort(consulPort);

        ConsulClient client = ConsulClient.create(vertx, options);

        client.deregisterService(serviceId)
                .onSuccess(it -> System.out.println("Todo service deregistered from Consul with ID: " + serviceId))
                .onFailure(it -> {
                    System.out.println("Failed to deregister Todo service from Consul: " + it.getMessage());
                });
    }
}
