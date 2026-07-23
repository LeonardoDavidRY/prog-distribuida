package com.programacion.distribuida.todo.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// DTO que consume la aplicación WEB: username, name, title, completed (según el enunciado)
@Getter
@Setter
@ToString
@Builder
public class TodoDto {

    private Long id;

    private String username;
    private String name;

    private String title;
    private Boolean completed;
}
