package com.programacion.distribuida.todo.dtos;

import lombok.*;

// Representa un elemento tal cual lo devuelve el sistema externo
// https://jsonplaceholder.typicode.com/todos (mismos nombres de campo del JSON:
// userId, id, title, completed) para que el REST Client lo deserialice directo.
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoSourceDto {
    private Long userId;
    private Long id;
    private String title;
    private Boolean completed;
}
