package ru.yandex.practicum.filmorate.controller;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
public class FilmController {

    FilmService filmService;
    FilmStorage filmStorage;

    @GetMapping("/films")
    public List<Film> getAllFilms(){
        return filmStorage.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable int id){
        return filmStorage.getFilm(id);
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmStorage.addFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film)  {
        return filmStorage.updateFilm(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike( @PathVariable int id , @PathVariable Integer userId)  {
        filmService.addLike(id,userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike( @PathVariable Integer id , @PathVariable Integer userId)  {
        filmService.deleteLike(id,userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms( @RequestParam(required = false) Optional<Integer> count)  {
        return filmService.popularFilms(count.orElse(10));
    }
}
