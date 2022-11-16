package ru.yandex.practicum.filmorate.exception;

public class FilmsOnDataBaseException extends RuntimeException {
    public FilmsOnDataBaseException(String message) {
        super("Ошибка базы данных при работе с фильмами: " + message);
    }
}
