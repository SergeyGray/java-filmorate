package ru.yandex.practicum.filmorate.exception;

public class LikeOnDataBaseException extends RuntimeException{
    public LikeOnDataBaseException(String message){
        super("Ошибка базы данных лайков: " + message);
    }
}
