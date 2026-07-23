package com.programacion.distribuida.todo.dtos;

import lombok.*;

// Representa (parcialmente) la respuesta de https://jsonplaceholder.typicode.com/users/{userId}
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    private String username;
    private String email;
}
