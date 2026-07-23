-- app-todo tiene su propia base de datos ("tododb"): esta migración corre en su
-- propio historial de Flyway, sin depender de ningún otro módulo del proyecto.
-- Solo crea el esquema ("Base de datos configurada automáticamente"); los datos
-- se cargan al arrancar consumiendo https://jsonplaceholder.typicode.com/todos
-- (ver TodoDataLoader), no se siembran aquí.
CREATE TABLE todos (
    id        BIGINT NOT NULL,
    title     VARCHAR(255),
    completed BOOLEAN,
    CONSTRAINT pk_todos PRIMARY KEY (id)
);
