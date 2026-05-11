package com.programacion.distribuida.customers.rest;

import com.programacion.distribuida.customers.db.LineItem;
import com.programacion.distribuida.customers.repo.LineItemRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/lineitems")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LineItemRest {

  @Inject
  LineItemRepository lineItemRepository;

  @Inject
  @ConfigProperty(name = "quarkus.http.port")
  Integer httpPort;

  @GET
  public List<LineItem> findAll() {
    return lineItemRepository.listAll();
  }

  @GET
  @Path("/{orderId}/{bookIsbn}")
  public Response getByCompositeKey(@PathParam("orderId") Long orderId, @PathParam("bookIsbn") String bookIsbn) {
    List<LineItem> items = lineItemRepository.listAll();
    return items.stream()
            .filter(it -> it.getOrderId().equals(orderId) && it.getBookIsbn().equals(bookIsbn))
            .map(it -> {
              it.setBookIsbn(it.getBookIsbn() + " - Puerto: " + httpPort);
              return it;
            })
            .findFirst()
            .map(Response::ok)
            .orElse(Response.status(Response.Status.NOT_FOUND))
            .build();
  }
}
