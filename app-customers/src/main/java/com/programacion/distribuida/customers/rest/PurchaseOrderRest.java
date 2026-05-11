package com.programacion.distribuida.customers.rest;

import com.programacion.distribuida.customers.db.PurchaseOrder;
import com.programacion.distribuida.customers.repo.PurchaseOrderRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/purchaseorders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PurchaseOrderRest {

  @Inject
  PurchaseOrderRepository purchaseOrderRepository;

  @Inject
  @ConfigProperty(name = "quarkus.http.port")
  Integer httpPort;

  @GET
  public List<PurchaseOrder> findAll() {
    return purchaseOrderRepository.listAll();
  }

  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") Long id) {
    return purchaseOrderRepository.findByIdOptional(id)
            .map(it -> {
              it.setTotal(it.getTotal() + httpPort);
              return it;
            })
            .map(Response::ok)
            .orElse(Response.status(Response.Status.NOT_FOUND))
            .build();
  }
}
