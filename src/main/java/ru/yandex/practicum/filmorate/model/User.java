package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

@Data
public class User {


   transient private final int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

    public User(int id, String email, String login, String name, LocalDate birthday) throws ValidationException {
        this.id = id;
        if(email.isBlank() || !email.contains("@")){
            throw new ValidationException("Неккоректный email");
        }else
            this.email = email;
        if(login.isBlank() || login.contains(" ")){
            throw new ValidationException("Неккоректный логин");
        }else
            this.login = login;
        if(name == null){
            this.name = login;
        }else
            this.name = name;
        if(birthday.isAfter(LocalDate.now())){
            throw new ValidationException("Неккоректная дата рождения");
        }else
            this.birthday = birthday;
    }


    public void setEmail(String email) throws  ValidationException{
    if(email.isBlank() || !email.contains("@")){
        throw new ValidationException("Неккоректный email");
    }else
        this.email = email;
    }
    public void setLogin(String login) throws  ValidationException{
        if(login.isBlank() || login.contains(" ")){
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
            throw new ValidationException("Неккоректная дата рождения");
        }else
            this.birthday = birthday;
    }

}

