package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Data
public class Film {
    private static final LocalDate DATE_FIRST_FILM_ON_HISTORY = LocalDate.of(1895,12,28);
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;

    public Film(int id, String name, String description, LocalDate releaseDate, Integer duration)
            throws ValidationException {
        this.id = id;
        if(name == null){
            throw new ValidationException("Пустое название фильма");
        }else{
            this.name = name;
        }
        if(description.length()>200){
            throw new ValidationException("Слишком длинное описание фильма");
        }else{
            this.description = description;
        }
        if(releaseDate.isBefore(DATE_FIRST_FILM_ON_HISTORY)){
            throw new ValidationException("Некорректное время выхода фильма");
        }else{
            this.releaseDate = releaseDate;
        }
        if(duration < 0){
            throw new ValidationException("Неккоректная длительность фильма");
        }else{
            this.duration = duration;
        }
    }

    public void setName(String name) throws ValidationException{
    if(name.isBlank()){
        throw new ValidationException("Пустое название фильма");
    }else{
        this.name = name;
    }

    }
    public void setDescription(String description) throws ValidationException{
        if(description.length()>200){
            throw new ValidationException("Слишком длинное описание фильма");
        }else{
            this.description = description;
        }
    }
    public void setReleaseDate(LocalDate releaseDate) throws ValidationException{
        if(releaseDate.isBefore(DATE_FIRST_FILM_ON_HISTORY)){
            throw new ValidationException("Некорректное время выхода фильма");
        }else{
            this.releaseDate = releaseDate;
        }
    }
    public void setDuration(Integer duration) throws ValidationException{
        if(duration < 0){
            throw new ValidationException("Неккоректная длительность фильма");
        }else{
            this.duration = duration;
        }
    }
}

