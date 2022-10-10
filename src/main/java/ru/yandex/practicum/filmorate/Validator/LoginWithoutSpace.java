package ru.yandex.practicum.filmorate.Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy=LoginWithoutSpaceValidator.class)
public @interface LoginWithoutSpace {
    String message() default "Логин не должен сожержать пробелов";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
