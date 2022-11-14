package ru.yandex.practicum.filmorate.storage.like;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.LikeOnDataBaseException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class LikeDbStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmDbStorage filmDbStorage;

    public void addLike(int idFilm, int idUser){
        try{
            String sqlQuery = "INSERT INTO likes(id_film, id_user) " +
                    "VALUES (?, ?)";
            jdbcTemplate.update(sqlQuery,idFilm,idUser);
            log.info("Добавлен лайк фильм с id{} от пользователя с id {}", idFilm, idUser);
        }catch (Exception ex){
            throw new LikeOnDataBaseException(String.format(
                    "Не удалось добавить лайк фильму c id %s",idFilm));
        }
    }

    public void deleteLike(int idFilm,int idUser){
            String sqlQuery = "DELETE  FROM likes  WHERE id_film = ? AND id_user = ?";
            int result = jdbcTemplate.update(sqlQuery,idFilm,idUser);
            if(result > 0){
                log.info("Удалён лайк фильму с id{} от пользователя с id {}", idFilm, idUser);
            } else {
                throw new LikeOnDataBaseException(String.format(
                        "Не удалось удалить лайк фильму c id %s",idFilm));
            }
    }

    public List<Film> getPopularFilms(int count){
        String sqlQuery = "SELECT fl.id, count(DISTINCT uf.id_user) as cnt FROM FILMS fl " +
        "LEFT JOIN likes uf ON fl.id = uf.id_film GROUP BY fl.id ORDER BY cnt DESC LIMIT ?";
        List<Integer> filmsRows= jdbcTemplate.query(sqlQuery,(rs, rowNum) -> rs.getInt("id"),count);
        List<Film> popularFilms = new ArrayList<>();
        for(Integer id: filmsRows) {
            popularFilms.add(filmDbStorage.getFilm(id));
        }
        return popularFilms;
    }
}