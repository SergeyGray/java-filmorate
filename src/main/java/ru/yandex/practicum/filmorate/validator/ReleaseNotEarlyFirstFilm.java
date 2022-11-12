package ru.yandex.practicum.filmorate.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy=ReleaseNotEarlyFirstFilmValidator.class)

public @interface ReleaseNotEarlyFirstFilm {
    String message() default "Дата выхода фильма не должна быть раньше 28 декабря 1897г.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
