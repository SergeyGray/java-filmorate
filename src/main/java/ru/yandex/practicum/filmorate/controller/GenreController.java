package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@AllArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        return genreService.getAllGenre();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenre(@PathVariable int id) {
        return genreService.getGenre(id);
    }
}
