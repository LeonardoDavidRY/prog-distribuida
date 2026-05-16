CREATE TABLE lineitem (
    order_id     BIGINT NOT NULL,
    book_isbn    VARCHAR(255) NOT NULL,
    idx          INTEGER,
    quantity     INTEGER,
    CONSTRAINT pk_lineitem PRIMARY KEY (order_id, book_isbn),
    CONSTRAINT fk_lineitem_order FOREIGN KEY (order_id) REFERENCES purchaseorder(id),
    CONSTRAINT fk_lineitem_book FOREIGN KEY (book_isbn) REFERENCES books(isbn)
);

INSERT INTO lineitem (order_id, book_isbn, quantity, idx) VALUES (1, '978-0-7432-7356-5', 2, 0);
INSERT INTO lineitem (order_id, book_isbn, quantity, idx) VALUES (2, '978-0-06-088328-6', 1, 0);
INSERT INTO lineitem (order_id, book_isbn, quantity, idx) VALUES (3, '978-0-7432-7357-2', 3, 0);
INSERT INTO lineitem (order_id, book_isbn, quantity, idx) VALUES (3, '978-0-06-088328-6', 1, 1);
