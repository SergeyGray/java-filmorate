package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MpaService {
    private final MpaDbStorage mpaDbStorage;

    public Mpa getMpa(int id) {
        Mpa mpa = mpaDbStorage.getMpa(id);
        log.info("Найден Mpa с id {}", id);
        return mpa;
    }

    public List<Mpa> getAllMpa() {
        List<Mpa> allMpa = mpaDbStorage.getAllMpa();
        log.info("Получены все Mpa");
        return allMpa;
    }
}
