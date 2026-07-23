package com.programacion.distribuida.todo.repo;

import com.programacion.distribuida.todo.db.Todo;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

// La tabla todos no guarda userId, así que no hay un findByUserId aquí: filtrar
// TODOs por usuario se resuelve en vivo contra el sistema externo (ver TodoRest).
@ApplicationScoped
@Transactional
public class TodoRepository implements PanacheRepositoryBase<Todo, Long> {
}
