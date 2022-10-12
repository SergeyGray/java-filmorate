package ru.yandex.practicum.filmorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmsOnMemoryException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class FilmController {

    private HashMap <Integer,Film> films = new HashMap<>();
    private int id = 1;

    @GetMapping("/films")
    public List<Film> getAllFilms(){
        return films.values().stream().collect(Collectors.toList());
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) throws FilmsOnMemoryException {
        if(films.containsValue(film)){
            throw new FilmsOnMemoryException("Данный фильм уже есть в памяти");
        }
        films.put(id,new Film(id, film.getName(), film.getDescription(),film.getReleaseDate(),film.getDuration()));
        ++id;
        log.info("Добавили новый фильм" + film.getName());
        return films.get(id-1);
    }
    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) throws FilmsOnMemoryException {
        if(films.containsKey(film.getId())){
            films.put(film.getId(),
                    new Film(film.getId(), film.getName(), film.getDescription(),film.getReleaseDate(),film.getDuration()));
            log.info("Обновили данные фильма" + film.getName());
            return film;
        }else {
            throw new FilmsOnMemoryException("Данный фильм отсутствует в памяти");
        }
    }
}
