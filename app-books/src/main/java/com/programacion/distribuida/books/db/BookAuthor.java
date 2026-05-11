package com.programacion.distribuida.books.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table(name = "book_author")
@Getter
@Setter
@IdClass(BookAuthorId.class)
public class BookAuthor {
    @Id
    @Column(name = "books_isbn")
    private String booksIsbn;
    
    @Id
    @Column(name = "authors_id")
    private Integer authorsId;
}

@Getter
@Setter
class BookAuthorId implements Serializable {
    private String booksIsbn;
    private Integer authorsId;
}
