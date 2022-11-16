package ru.yandex.practicum.filmorate.exception;

public class LikeOnDataBaseException extends RuntimeException {
    public LikeOnDataBaseException(String message) {
        super("Ошибка базы данных при работе с лайками: " + message);
    }
}
