package ru.yandex.practicum.filmorate.exception;

public class FilmsOnMemoryException extends RuntimeException {
    public FilmsOnMemoryException(String message) {
        super("Ошибка коллекции фильмов: " + message);
    }
}
