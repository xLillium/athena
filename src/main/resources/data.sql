INSERT INTO users (id, first_name, last_name) VALUES (101, 'Alice', 'Alpha');
INSERT INTO users (id, first_name, last_name) VALUES (102, 'Bob', 'Bravo');

INSERT INTO books (id, title, author, isbn) VALUES (201, '1984', 'George Orwell', '1234567890');
INSERT INTO books (id, title, author, isbn) VALUES (202, '1985', 'George Orwell', '1234567891');

INSERT INTO user_books (user_id, book_id) VALUES (101, 201);
INSERT INTO user_books (user_id, book_id) VALUES (102, 202);
