package ru.yandex.practicum.filmorate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FilmValidationTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private Film film = new Film(1,"testName","testDescription",
            LocalDate.of(2020,10,10),100);

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    @Test
    public void createFilmBlankName() {
        film.setName("");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1,violations.size());
        ConstraintViolation<Film> violation = violations.iterator().next();
        assertEquals("Имя не может быть пустым", violation.getMessage());
    }
    @Test
    public void createFilmDescriptionMore200Characters() {
        film.setDescription(new String("a".repeat(201)));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1,violations.size());
        ConstraintViolation<Film> violation = violations.iterator().next();
        assertEquals("Длина описания не может быть более 200 символов", violation.getMessage());
    }
    @Test
    public void createFilmNotPositiveDuration() {
        film.setDuration(0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1,violations.size());
        ConstraintViolation<Film> violation = violations.iterator().next();
        assertEquals("Продолжительность фильма должа быть более 0", violation.getMessage());
    }
    @Test
    public void createFilmEarlyFirstFilmRelease() {
        film.setReleaseDate(LocalDate.of(1700,10,10));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1,violations.size());
        ConstraintViolation<Film> violation = violations.iterator().next();
        assertEquals("Дата выхода фильма не должна быть раньше 28 декабря 1897г.", violation.getMessage());
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }
}
