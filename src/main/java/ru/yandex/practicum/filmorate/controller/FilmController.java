package ru.yandex.practicum.filmorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DataBaseException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class FilmController {
    private HashMap <Integer,Film> films = new HashMap<>();
    private int id = 1;

    @GetMapping("/films")
    public Collection<Film> getAllFilms(){
        return films.values();
    }

    @PostMapping("/films")
    public Film AddFilm(@Valid @RequestBody Film film) throws ValidationException, DataBaseException {
        if(films.containsValue(film)){
            throw new DataBaseException("Данный фильм уже есть в базе");
        }else {
            films.put(id,new Film(id, film.getName(), film.getDescription(),film.getReleaseDate(),film.getDuration()));
            ++id;
            log.info("Добавили новый фильм" + film.getName());
        }
        return films.get(id-1);
    }
    @PutMapping("/films")
    public Film UpdateFilm(@Valid @RequestBody Film film) throws  ValidationException,DataBaseException{
        if(films.containsKey(film.getId())){
            films.put(film.getId(),
                    new Film(id, film.getName(), film.getDescription(),film.getReleaseDate(),film.getDuration()));
            log.info("Обновили данные фильма" + film.getName());
            return film;
        }else {
            throw new DataBaseException("Данный фильм отсутствует в базе");
        }
    }

}
