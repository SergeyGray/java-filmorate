DELETE FROM likes;
DELETE FROM friends;
DELETE FROM genres_for_films;
DELETE FROM users;
DELETE FROM films;

ALTER TABLE users ALTER COLUMN id RESTART WITH 1;
ALTER TABLE films ALTER COLUMN id RESTART WITH 1;

MERGE INTO genres KEY(id) VALUES (1,'Комедия'),(2,'Драма'), (3,'Мультфильм'),(4,'Триллер'),(5,'Документальный'),(6,'Боевик');
MERGE INTO mpa KEY(id) VALUES (1,'G'),(2,'PG'), (3,'PG-13'),(4,'R'),(5,'NC-17');
