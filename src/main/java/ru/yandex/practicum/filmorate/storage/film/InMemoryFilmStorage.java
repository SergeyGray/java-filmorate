package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmsOnMemoryException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage{

    private final HashMap<Integer,Film> films;
    private int id;

    public InMemoryFilmStorage() {
        films = new HashMap<>();
        id = 1;
    }

    @Override
    public Film getFilm(int id) {
        if(!films.containsKey(id)){
            throw new FilmsOnMemoryException(String.format(" Фильм с id %s отсутствует в памяти",id));
        }
        return films.get(id);
    }

    @Override
    public List<Film> getAllFilms() {
        return films.values().stream().collect(Collectors.toList());
    }

    @Override
    public Film addFilm(Film film) {
//        if(films.containsValue(film)){
//            throw new FilmsOnMemoryException(String.format("Фильм %s уже есть в памяти",film.getName()));
//        }
//        films.put(id,new Film(id, film.getName(), film.getDescription(),film.getMpa().getId(),film.getReleaseDate(),film.getDuration()));
//        ++id;
//        log.info("Добавили новый фильм: {}",film.getName());
//        return films.get(id-1);
        return null;
    }

    @Override
    public Film updateFilm(Film film) {
//        if(films.containsKey(film.getId())){
//            films.put(film.getId(),
//       //             new Film(film.getId(), film.getName(), film.getDescription(),film.getGenre(),film.getMpa(),film.getReleaseDate(),film.getDuration()));
//            log.info("Обновили данные фильма: {}",film.getName());
//            return film;
//        }else {
//            throw new FilmsOnMemoryException(String.format("Фильм %s отсутствует в памяти",film.getName()));
//        }
        return null;
    }

    @Override
    public void deleteFilm(Film film) {
        if(films.containsValue(film)){
            films.remove(film);
            log.info("Удалили фильм из памяти: {}",film.getName());
        } else{
            throw new FilmsOnMemoryException(String.format("Фильм %s отсутствует в памяти",film.getName()));
        }
    }

}
