package ru.yandex.practicum.filmorate.exception;

public class GenreOnDataBaseException extends RuntimeException{
    public GenreOnDataBaseException (String message){
        super("Ошибка базы данных жанров: " + message);
    }
}
