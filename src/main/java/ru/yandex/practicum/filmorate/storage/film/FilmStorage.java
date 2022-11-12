package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film getFilm(int id);

    List<Film> getAllFilms();

    Film addFilm(Film film);

    Film updateFilm(Film film);

    void deleteFilm(Film film);
}
