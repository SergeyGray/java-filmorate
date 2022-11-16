package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GenreService {
    private final GenreDbStorage genreDbStorage;


    public Genre getGenre(int id) {
        Genre genre = genreDbStorage.getGenre(id);
        log.info("Найден жанр с id {}", id);
        return genre;
    }

    public List<Genre> getAllGenre() {
        List<Genre> allGenres = genreDbStorage.getAllGenres();
        log.info("Получены все жанры");
        return allGenres;
    }

    public List<Genre> getGenresForFilms(int id) {
        List<Genre> allGenresForFilms = genreDbStorage.getGenresForFilms(id);
        log.info("Получены все жанры для фильма с id {}", id);
        return allGenresForFilms;
    }
}
