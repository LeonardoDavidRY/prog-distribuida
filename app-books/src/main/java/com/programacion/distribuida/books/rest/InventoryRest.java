package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.db.Inventory;
import com.programacion.distribuida.books.repo.InventoryRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventoryRest {

  @Inject
  InventoryRepository inventoryRepository;

  @Inject
  @ConfigProperty(name = "quarkus.http.port")
  Integer httpPort;

  @GET
  public List<Inventory> findAll() {
    return inventoryRepository.listAll();
  }

  @GET
  @Path("/{bookIsbn}")
  public Response getByBookIsbn(@PathParam("bookIsbn") String bookIsbn) {
    return inventoryRepository.findByIdOptional(bookIsbn)
            .map(it -> {
              it.setBookIsbn(it.getBookIsbn() + " - Puerto: " + httpPort);
              return it;
            })
            .map(Response::ok)
            .orElse(Response.status(Response.Status.NOT_FOUND))
            .build();
  }
}
