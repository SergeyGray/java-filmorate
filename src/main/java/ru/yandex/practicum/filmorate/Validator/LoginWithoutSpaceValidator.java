package ru.yandex.practicum.filmorate.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginWithoutSpaceValidator implements ConstraintValidator<LoginWithoutSpace,String> {
    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext){
        return !login.contains(" ");
    }
}
