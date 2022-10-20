package ru.yandex.practicum.filmorate.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmsOnMemoryException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }
    public void addLike(int id,int userId ){
        Film film = filmStorage.getFilm(id);
        User user = userStorage.getUser(userId);
        if(film.getLikes().contains(userId)){
            throw new FilmsOnMemoryException(String.format("Фильм %s уже был оценён пользователем %s",
                    film.getName(),user.getLogin()));
        }
        film.getLikes().add(userId);
        log.info("Фильму {} добавлен лайк от пользователя {}",
                film.getName(),user.getName());
    }
    public void deleteLike(int id,int userId ){
        Film film = filmStorage.getFilm(id);
        User user = userStorage.getUser(id);
        if(!film.getLikes().contains(userId)){
            throw new FilmsOnMemoryException(String.format("Фильм %s ещё не был оценён пользователь %s",
                    film.getName(),user.getLogin()));
        }
        film.getLikes().remove(userId);
        log.info("Пользователь {} удалил лайк у фильма {}",
                user.getName(),film.getName());
    }
    public List<Film> popularFilms(Integer count){
        List<Film> popularFilm = filmStorage.getAllFilms().stream().collect(Collectors.toList());
        popularFilm.sort((f1, f2)->(-1*Integer.compare(f1.getLikes().size(),f2.getLikes().size())));
        if(count > popularFilm.size()){
            count  = popularFilm.size();
        }
        return popularFilm.subList(0,count);
    }
}
