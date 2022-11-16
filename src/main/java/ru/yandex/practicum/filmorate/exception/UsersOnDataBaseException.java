package ru.yandex.practicum.filmorate.exception;

public class UsersOnDataBaseException extends RuntimeException {
    public UsersOnDataBaseException(String message) {
        super("Ошибка базы данных при работе с пользователями: " + message);
    }
}
