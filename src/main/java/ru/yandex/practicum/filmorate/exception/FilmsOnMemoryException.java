package ru.yandex.practicum.filmorate.exception;

public class FilmsOnMemoryException extends Exception{
    public FilmsOnMemoryException(String message){
        super("Ошибка коллекции фильмов:" + message);
    }
}
