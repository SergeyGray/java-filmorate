package ru.yandex.practicum.filmorate.exception;

public class MpaOnDataBaseException extends RuntimeException {
    public MpaOnDataBaseException(String message) {
        super("Ошибка базы данных при работе с Mpa: " + message);
    }
}
