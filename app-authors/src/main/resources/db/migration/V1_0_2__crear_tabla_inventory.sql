CREATE TABLE inventories (
    book_isbn    VARCHAR(255) NOT NULL,
    supplied     INTEGER,
    sold         INTEGER,
    version      INTEGER,
    CONSTRAINT pk_inventories PRIMARY KEY (book_isbn),
    CONSTRAINT fk_inventories_books FOREIGN KEY (book_isbn) REFERENCES books(isbn)
);

INSERT INTO inventories (book_isbn, supplied, sold, version) VALUES ('978-0-7432-7356-5', 100, 45, 0);
INSERT INTO inventories (book_isbn, supplied, sold, version) VALUES ('978-0-06-088328-6', 150, 78, 0);
INSERT INTO inventories (book_isbn, supplied, sold, version) VALUES ('978-0-7432-7357-2', 80, 32, 0);
