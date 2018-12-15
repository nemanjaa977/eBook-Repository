INSERT INTO authority (name)
VALUES ('ROLE_ADMIN');
INSERT INTO authority (name)
VALUES ('ROLE_USER');

INSERT INTO users(first_name, last_name, username, password, type)
VALUES ('Marko', 'Markovic', 'mare', '$2a$10$Ga5/65GDsF2g6jMg5EKCSO1TFyV/UD6wVSbEooLzHqgPa5viKKUX6', 'Admin');
INSERT INTO users(first_name, last_name, username, password, type)
VALUES ('Zarko', 'Zarkovic', 'zare', '$2a$10$Ga5/65GDsF2g6jMg5EKCSO1TFyV/UD6wVSbEooLzHqgPa5viKKUX6', 'User');

INSERT INTO user_authority(user_id, authority_id)
VALUES (1, 1);
INSERT INTO user_authority(user_id, authority_id)
VALUES (2, 2);

INSERT INTO language(name)
VALUES ('Serbian');

INSERT INTO category(name, user_id)
VALUES ('Fantasy', 1);
INSERT INTO category(name)
VALUES ('Action');
INSERT INTO category(name)
VALUES ('History');
INSERT INTO category(name)
VALUES ('Autobiography');
INSERT INTO category(name)
VALUES ('Drama');
INSERT INTO category(name)
VALUES ('Philosophy');
INSERT INTO category(name)
VALUES ('Horror');
INSERT INTO category(name)
VALUES ('Comedy');
INSERT INTO category(name)
VALUES ('Poetry');


INSERT INTO ebooks(title, author, keywords, publication_year, filename, mime, language_id, category_id, user_id)
VALUES ('The Hobbit', 'Dzon Ronald Rejel Tolkin', '', 2012, 'download', '', 1, 1, 1);

