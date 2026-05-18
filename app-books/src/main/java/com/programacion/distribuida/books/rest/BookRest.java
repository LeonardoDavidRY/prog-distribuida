package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.clients.AuthorRestClient;
import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.dtos.BookDto;
import com.programacion.distribuida.books.repo.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

@Transactional
@ApplicationScoped
public class BookRest {

    //@Inject
    final BookRepository bookRepository;
    //@Inject
    //@RestClient //proxy actumatico
    final AuthorRestClient client;

    @Inject
    public BookRest(BookRepository bookRepository, @RestClient AuthorRestClient client) {
        this.bookRepository = bookRepository;
        this.client = client; //se pone final para obligar a que se iniciace como el constructor, evita este metodo
    }

    //@RequiredArgsConstructor
//    public BookRest(BookRepository bookRepository, AuthorRestClient client) {
//        this.bookRepository = bookRepository;
//        this.client = client; //se pone final para obligar a que se iniciace como el constructor, evita este metodo
//    } el required entiende el constructor definido como el inject
//para el ciclo de vida se inicia con el post y el destroy para matarlo
//    @PostConstruct
//    public void init() {
//        client = RestClientBuilder.newBuilder()
//                .baseUri("http://127.0.0.1:8070")
//                .build(AuthorRestClient.class);
//    }


    @GET
    public List<BookDto> findAll() {
        return bookRepository.streamAll()
                .map(book -> {
                    //consultar los autores en http://127.0.0.1:8070
                    var authors = client.findByBook(book.getIsbn());

                    return BookDto.builder()
                            .isbn(book.getIsbn())
                            .title(book.getTitle())
                            .price(book.getPrice())
                            .authors(authors)
                            .inventorySold(book.getInventory() != null ? book.getInventory().getSold() : null)
                            .inventorySupplied(book.getInventory() != null ? book.getInventory().getSupplied() : null)
                            .build();
                })
                .toList();
    }


    @GET
    @Path("/{isbn}")
    public Response findByIsbn(@PathParam("isbn") String isbn) {

        return bookRepository.findByIdOptional(isbn)
                .map(book -> {
                    try {
//                        AuthorRestClient client =  RestClientBuilder.newBuilder()
//                                .baseUri("http://127.0.0.1:8070")
//                                .build(AuthorRestClient.class);
                        var authors = client.findByBook(isbn);
                        return BookDto.builder()
                                .isbn(book.getIsbn())
                                .title(book.getTitle())
                                .price(book.getPrice())
                                .authors(authors)
                                .build();
                    } catch (Exception e) {
                        return BookDto.builder()
                                .isbn(book.getIsbn())
                                .title(book.getTitle())
                                .price(book.getPrice())
                                .build();
                    }
                })
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @PUT
    @Path("/{isbn}")
    public Response update(@PathParam("isbn") String isbn, Book book) {
        bookRepository.update(isbn, book);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{isbn}")
    public Response delete(@PathParam("isbn") String isbn) {
        bookRepository.deleteById(isbn);
        return Response.ok().build();
    }

    @POST
    public Response update(Book book) {
        bookRepository.persist(book);
        var uri = UriBuilder.fromUri("/books/{isbn}")
                .build(book.getIsbn());
        return Response.created(uri).build();
    }

}
