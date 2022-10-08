package ru.yandex.practicum.filmorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

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
    public Film AddFilm(@RequestBody Film film) throws ValidationException {
        if(films.containsValue(film)){
            log.info("Такой фильм уже существует" + film.getName());
        }else {
            films.put(id,new Film(id, film.getName(), film.getDescription(),film.getReleaseDate(),film.getDuration()));
            ++id;
            log.info("Добавили новый фильм" + film.getName());
        }
        return films.get(id-1);
    }
    @PutMapping("/films")
    public Film UpdateFilm(@RequestBody Film film) throws  ValidationException{
        if(films.containsValue(film)){
            films.put(film.getId(),film);
            log.info("Заменили данные фильма" + film.getName());
            return film;
        }else {
            log.info("Такой фильм уже существует" + film.getName());
            throw new ValidationException("Данный фильм отсутствует в базе");
        }
    }

}
