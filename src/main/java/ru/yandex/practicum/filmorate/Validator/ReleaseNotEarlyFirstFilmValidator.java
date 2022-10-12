package ru.yandex.practicum.filmorate.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseNotEarlyFirstFilmValidator implements ConstraintValidator<ReleaseNotEarlyFirstFilm, LocalDate> {
    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext constraintValidatorContext){
        return releaseDate.isAfter(LocalDate.of(1895,12,28));
    }
}
