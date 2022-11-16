package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmsOnDataBaseException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreService genreService;
    private final MpaService mpaService;

    @Override
    public Film getFilm(int id) {
        String sqlQuery = "SELECT * FROM films WHERE id=?";
        Film film;
        try {
            film = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
        } catch (EmptyResultDataAccessException ex) {
            throw new FilmsOnDataBaseException(String.format(" Фильм с id %s отсутствует в базе данных", id));
        }
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "SELECT * FROM films";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "INSERT INTO films(name, description, mpa, release_date, duration) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setInt(3, film.getMpa().getId());
            ps.setDate(4, Date.valueOf(film.getReleaseDate()));
            ps.setInt(5, film.getDuration());
            return ps;
        }, keyHolder);
        int id = Objects.requireNonNull(keyHolder.getKey().intValue());
        film.setId(id);
        if (film.getGenres() != null) {
            batchAddGenresForFilm(film);
        }
        log.info("Добален новый фильм с id {}", id);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "UPDATE films SET name = ?, description = ?, mpa = ?, release_date = ?, duration = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sqlQuery
                , film.getName()
                , film.getDescription()
                , film.getMpa().getId()
                , film.getReleaseDate().toString()
                , film.getDuration()
                , film.getId());
        String sqlQueryForClearGenre = "DELETE FROM genres_for_films WHERE id_film = ?";
        jdbcTemplate.update(sqlQueryForClearGenre, film.getId());
        if (film.getGenres() != null) {
            batchAddGenresForFilm(film);
        }
        log.info(String.format("Фильм %s обновлён", film.getName()));
        return getFilm(film.getId());
    }

    @Override
    public void deleteFilm(Film film) {
        String sqlQuery = "DELETE FROM films WHERE  id = ?";
        try {
            jdbcTemplate.update(sqlQuery, film.getId());
            log.info("Фильм {} удалён из базы данных", film.getName());
        } catch (EmptyResultDataAccessException ex) {
            throw new FilmsOnDataBaseException(String.format("Фильм %s отсутствует в базе данных", film.getName()));
        }
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .genres(genreService.getGenresForFilms(resultSet.getInt("id")))
                .mpa(mpaService.getMpa(resultSet.getInt("mpa")))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .build();
    }

    private void batchAddGenresForFilm(Film film) {
        String sqlQueryForGenre = "MERGE INTO genres_for_films VALUES (?, ?) ";
        jdbcTemplate.batchUpdate(sqlQueryForGenre, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, film.getId());
                ps.setInt(2, film.getGenres().get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return film.getGenres().size();
            }
        });
    }
}
