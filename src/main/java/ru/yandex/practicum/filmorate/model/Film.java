package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.ReleaseNotEarlyFirstFilm;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Data
@Builder
public class Film {

    private final Set<Integer> likes;
    private int id;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @Size(max = 200, message = "Длина описания не может быть более 200 символов")
    private String description;
    private List<Genre> genres;
    private Mpa mpa;
    @ReleaseNotEarlyFirstFilm
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должа быть более 0")
    private Integer duration;

}

