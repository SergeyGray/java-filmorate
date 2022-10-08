package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ValidationException extends Exception{
    public ValidationException(String message){
        super("Ошибка валидации:" + message);
    }

}
