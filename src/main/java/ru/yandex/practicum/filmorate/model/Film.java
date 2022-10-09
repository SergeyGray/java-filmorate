package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@Slf4j
public class Film {
    private static final LocalDate DATE_FIRST_FILM_ON_HISTORY = LocalDate.of(1895,12,28);
    private int id;
    @NotBlank
    private String name;
    @Size(max =200)
    private String description;

    private LocalDate releaseDate;
    @Positive
    private Integer duration;

    public Film(int id, String name, String description, LocalDate releaseDate, Integer duration)
            throws ValidationException {
        this.id = id;
        setName(name);
        setDescription(description);
        setReleaseDate(releaseDate);
        setDuration(duration);
    }

    public void setName(String name) throws ValidationException{
    if(name == null || name.isBlank()){
        log.info("Ошибка валидация - пустое название фильма");
        throw new ValidationException("Пустое название фильма");
    }else{
        this.name = name;
    }

    }
    public void setDescription(String description) throws ValidationException{
        if(description.length()>200){
            log.info("Ошибка валидация - Слишком длинное описание фильма");
            throw new ValidationException("Слишком длинное описание фильма");
        }else{
            this.description = description;
        }
    }
    public void setReleaseDate(LocalDate releaseDate) throws ValidationException{
        if(releaseDate.isBefore(DATE_FIRST_FILM_ON_HISTORY)){
            log.info("Ошибка валидация - Некорректное время выхода фильма");
            throw new ValidationException("Некорректное время выхода фильма");
        }else{
            this.releaseDate = releaseDate;
        }
    }
    public void setDuration(Integer duration) throws ValidationException{
        if(duration <= 0){
            log.info("Ошибка валидация - некорректная длительность фильма");
            throw new ValidationException("Неккоректная длительность фильма");
        }else{
            this.duration = duration;
        }
    }
}

