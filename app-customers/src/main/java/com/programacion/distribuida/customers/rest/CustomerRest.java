package com.programacion.distribuida.customers.rest;

import com.programacion.distribuida.customers.db.Customer;
import com.programacion.distribuida.customers.repo.CustomerRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerRest {

  @Inject
  CustomerRepository customerRepository;

  @Inject
  @ConfigProperty(name = "quarkus.http.port")
  Integer httpPort;

  @GET
  public List<Customer> findAll() {
    return customerRepository.listAll();
  }

  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") Long id) {
    return customerRepository.findByIdOptional(id)
            .map(it -> {
              it.setName(it.getName() + " - Puerto: " + httpPort);
              return it;
            })
            .map(Response::ok)
            .orElse(Response.status(Response.Status.NOT_FOUND))
            .build();
  }
}
