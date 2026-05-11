package com.programacion.distribuida.books.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book {
    @Id
    private String isbn;
    private String title;
    private Double price;
    private Integer version;
}
