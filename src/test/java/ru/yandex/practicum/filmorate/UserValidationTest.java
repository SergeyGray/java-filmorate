package ru.yandex.practicum.filmorate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserValidationTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private User user = User.builder()
            .id(1)
            .email("test@Mail.ru")
            .login("testLogin")
            .name("testName")
            .birthday(LocalDate.now())
            .build();
    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    @Test
    public void CreateUserWrongEmail(){
        user.setEmail("testMail.ru");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1,violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Неккоректный email", violation.getMessage());
    }
    @Test
    public void CreateUserBlankEmail(){
        user.setEmail("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1,violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Email не может быть пустым", violation.getMessage());
    }
    @Test
    public void createUserLoginWithSpace(){
        user.setLogin("test login");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1,violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Логин не должен сожержать пробелов", violation.getMessage());
    }
    @Test
    public void createUserBlankLogin(){
        user.setLogin("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1,violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Логин не может быть пустым", violation.getMessage());
    }
    @Test
    public void createUserBirthdayOnFuture(){
        user.setBirthday(LocalDate.of(2030,10,10));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1,violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Дата рождения не может быть в будушем", violation.getMessage());
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

}
