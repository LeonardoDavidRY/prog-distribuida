package com.programacion.distribuida.todo.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// La DB de este microservicio SOLO guarda información de TODOs: id, title, completed.
// No guarda ni usuarios ni el userId dueño del TODO: qué TODOs pertenecen a qué usuario
// se resuelve en vivo contra el sistema externo (https://jsonplaceholder.typicode.com/todos?userId=)
// y contra https://jsonplaceholder.typicode.com/users/{userId} para username/name.
// La tabla "todos" la crea la migración V1_0_7 de app-authors (ver nota en build.gradle.kts).
@Entity
@Table(name = "todos")
@Getter @Setter
@ToString
public class Todo {

    // El id no es autogenerado: replica el id del recurso TODO del sistema origen
    // (https://jsonplaceholder.typicode.com/todos), igual que Book usa isbn como llave natural.
    @Id
    private Long id;

    private String title;

    private Boolean completed;
}
