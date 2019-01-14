INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_USER');

INSERT INTO language(name) VALUES ('Serbian');

INSERT INTO category(name) VALUES ('Fantasy');
INSERT INTO category(name) VALUES ('Action');
INSERT INTO category(name) VALUES ('History');
INSERT INTO category(name) VALUES ('Autobiography');
INSERT INTO category(name) VALUES ('Drama');
INSERT INTO category(name) VALUES ('Philosophy');
INSERT INTO category(name) VALUES ('Horror');

INSERT INTO users(first_name, last_name, username, password, type, category_id) VALUES ('Marko', 'Markovic', 'mare', '$2a$10$Ga5/65GDsF2g6jMg5EKCSO1TFyV/UD6wVSbEooLzHqgPa5viKKUX6', 'Admin', 1);
INSERT INTO users(first_name, last_name, username, password, type, category_id) VALUES ('Zarko', 'Zarkovic', 'zare', '$2a$10$Ga5/65GDsF2g6jMg5EKCSO1TFyV/UD6wVSbEooLzHqgPa5viKKUX6', 'User', 3);

INSERT INTO user_authority(user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority(user_id, authority_id) VALUES (2, 2);

INSERT INTO ebooks(title, author, keywords, publication_year, filename, mime, language_id, category_id, user_id) VALUES ('The Hobbit', 'Dzon Ronald Rejel Tolkin', '', 2012, 'download', '', 1, 1, 1);
INSERT INTO ebooks(title, author, keywords, publication_year, filename, mime, language_id, category_id, user_id) VALUES ('Harry Potter', 'J. K. Rowling', '', 2007, 'download', '', 1, 1, 2);

