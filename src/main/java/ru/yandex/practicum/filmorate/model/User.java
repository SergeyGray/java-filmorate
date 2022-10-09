package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@Slf4j
public class User {

   transient private final int id;
   @Email
    private String email;
   @NotBlank
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;

    public User(int id, String email, String login, String name, LocalDate birthday) throws ValidationException {
        this.id = id;
        setEmail(email);
        setLogin(login);
        setName(name);
        setBirthday(birthday);
    }


    public void setEmail(String email) throws  ValidationException{
    if(email == null || email.isBlank() || !email.contains("@")){
        log.info("Ошибка валидации - некорретный email");
        throw new ValidationException("Неккоректный email");
    }else
        this.email = email;
    }
    public void setLogin(String login) throws  ValidationException{
        if(login == null|| login.isBlank() || login.contains(" ")){
            log.info("Ошибка валидации - некорретный логин");
            throw new ValidationException("Неккоректный логин");
        }else
            this.login = login;
    }
    public void setName(String name) {
        if(name == null){
            this.name = login;
        }else
            this.name = name;
    }
    public void setBirthday(LocalDate birthday) throws  ValidationException{
        if(birthday.isAfter(LocalDate.now())){
            log.info("Ошибка валидации - некорретная дата рождения");
            throw new ValidationException("Неккоректная дата рождения");
        }else
            this.birthday = birthday;
    }

}

