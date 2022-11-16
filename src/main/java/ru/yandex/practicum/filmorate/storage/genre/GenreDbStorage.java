package ru.yandex.practicum.filmorate.storage.genre;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.GenreOnDataBaseException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@AllArgsConstructor
public class GenreDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public Genre getGenre(int id) {
        String sqlQuery = "SELECT * FROM genres WHERE id=?";
        Genre genre;
        try {
            genre = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
        } catch (EmptyResultDataAccessException ex) {
            throw new GenreOnDataBaseException(String.format("Жанр с id %s не найден", id));
        }
        return genre;
    }

    public List<Genre> getAllGenres() {
        String sqlQuery = "SELECT * FROM genres";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    public List<Genre> getGenresForFilms(int id) {
        String sqlQuery = "SELECT f.id, f.genre FROM genres f INNER JOIN " +
                "genres_for_films gff on f.id = gff.id_genre WHERE id_film =?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre, id);
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("genre"))
                .build();

    }
}
