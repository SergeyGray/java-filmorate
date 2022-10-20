package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.Validator.ReleaseNotEarlyFirstFilm;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Slf4j
@AllArgsConstructor
public class Film {

    private int id;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @Size(max =200, message = "Длина описания не может быть более 200 символов")
    private String description;
    @ReleaseNotEarlyFirstFilm
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должа быть более 0")
    private Integer duration;
    private final Set<Integer> likes= new HashSet<>();

}

