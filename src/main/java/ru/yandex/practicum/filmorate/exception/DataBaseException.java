package ru.yandex.practicum.filmorate.exception;

public class DataBaseException extends Exception{
    public DataBaseException(String message){
        super("Ошибка базы данных:" + message);
    }
}
