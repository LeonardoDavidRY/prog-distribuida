CREATE TABLE inventory (
    book_isbn    VARCHAR(255) NOT NULL,
    supplied     INTEGER,
    sold         INTEGER,
    version      INTEGER,
    CONSTRAINT pk_inventory PRIMARY KEY (book_isbn),
    CONSTRAINT fk_inventory_book FOREIGN KEY (book_isbn) REFERENCES book(isbn)
);

INSERT INTO inventory (book_isbn, supplied, sold, version) VALUES ('978-0-7432-7356-5', 100, 45, 0);
INSERT INTO inventory (book_isbn, supplied, sold, version) VALUES ('978-0-06-088328-6', 150, 78, 0);
INSERT INTO inventory (book_isbn, supplied, sold, version) VALUES ('978-0-7432-7357-2', 80, 32, 0);
