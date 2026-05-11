CREATE TABLE book_author (
    books_isbn   VARCHAR(255) NOT NULL,
    authors_id   INTEGER NOT NULL,
    CONSTRAINT pk_book_author PRIMARY KEY (books_isbn, authors_id),
    CONSTRAINT fk_book_author_book FOREIGN KEY (books_isbn) REFERENCES book(isbn),
    CONSTRAINT fk_book_author_author FOREIGN KEY (authors_id) REFERENCES authors(id)
);

INSERT INTO book_author (books_isbn, authors_id) VALUES ('978-0-7432-7356-5', 1);
INSERT INTO book_author (books_isbn, authors_id) VALUES ('978-0-06-088328-6', 2);
INSERT INTO book_author (books_isbn, authors_id) VALUES ('978-0-7432-7357-2', 3);
