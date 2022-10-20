package ru.yandex.practicum.filmorate.exception;

public class UsersOnMemoryException extends RuntimeException {
    public UsersOnMemoryException(String message){
        super("Ошибка коллекции пользовтелей: " + message);
    }
}
