package com.programacion.distribuida.authors.repo;

import com.programacion.distribuida.authors.db.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.PathParam;

import java.util.List;

@ApplicationScoped
@Transactional
public class AuthorRepository implements PanacheRepositoryBase<Author, Integer> {

    public List<Author> findByBook(@PathParam("isbn") String isbn) {
        return this.find("select o.author from BookAuthor o where o.id.bookIsbn = ?1", isbn).list();
    }
}
