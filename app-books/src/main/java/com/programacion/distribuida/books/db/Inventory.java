package com.programacion.distribuida.books.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
public class Inventory {
    @Id
    @Column(name = "book_isbn")
    private String bookIsbn;
    private Integer supplied;
    private Integer sold;
    private Integer version;
}
