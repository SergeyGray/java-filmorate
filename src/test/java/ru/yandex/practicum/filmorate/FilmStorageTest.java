package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmStorageTest {
    private final FilmDbStorage filmStorage;

    private Film film = Film.builder()
            .name("testName")
            .description("testDescription")
            .releaseDate(LocalDate.of(2020,10,10))
            .duration(100)
            .mpa(Mpa.builder().id(1).build())
            .build();
    private Film film2= Film.builder()
            .name("testName2")
            .description("testDescription2")
            .releaseDate(LocalDate.of(2022,1,1))
            .duration(1)
            .mpa(Mpa.builder().id(1).build())
            .build();
    @Test
    public void testFilmCreate() {
        filmStorage.addFilm(film);
        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilm(1));
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1));
    }
    @Test
    public void testFilmUpdate() {
        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilm(1));
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "testName"));
        film.setName("Update name");
        film.setId(1);
        filmStorage.updateFilm(film);
        filmOptional = Optional.ofNullable(filmStorage.getFilm(1));
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "Update name"));
    }
    @Test
    public void testGetAllFilm() {
        filmStorage.addFilm(film2);
        List<Film> allFilms= filmStorage.getAllFilms();
        System.out.println(allFilms);
        assertEquals(allFilms.size(),2);
    }
    @Test
    public void testDeleteFilm() {
        film2.setId(2);
        filmStorage.deleteFilm(film2);
        List<Film> allFilms= filmStorage.getAllFilms();
        assertEquals(allFilms.size(),1);
    }
}
