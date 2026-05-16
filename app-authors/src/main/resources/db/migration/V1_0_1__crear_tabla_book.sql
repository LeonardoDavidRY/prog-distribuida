CREATE TABLE books (
    isbn         VARCHAR(255) NOT NULL,
    title        VARCHAR(255),
    price        NUMERIC(38,2),
    version      INTEGER,
    CONSTRAINT pk_books PRIMARY KEY (isbn)
);

INSERT INTO books (isbn, title, price, version) VALUES ('978-0-7432-7356-5', 'Ficciones', 15.99, 0);
INSERT INTO books (isbn, title, price, version) VALUES ('978-0-06-088328-6', 'Cien años de soledad', 18.99, 0);
INSERT INTO books (isbn, title, price, version) VALUES ('978-0-7432-7357-2', 'La casa de los espíritus', 16.99, 0);
