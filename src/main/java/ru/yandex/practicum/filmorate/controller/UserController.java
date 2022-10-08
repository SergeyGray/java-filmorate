package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class UserController {

    private HashMap<Integer, User> users = new HashMap<Integer, User>();
    private int id = 1;

    @GetMapping("/users")
    public Collection<User> getAllUsers(){
        return users.values();
    }
    @PostMapping("/users")
    public User AddUser(@RequestBody User user) throws ValidationException{
        if(users.containsValue(user)){
            log.info("Такой пользовтель уже существует" + user.getLogin());
        }else {
            users.put(id,new User(id,user.getEmail(), user.getLogin(), user.getName(), user.getBirthday()));
            ++id;
            log.info("Добавлен новый пользователь" + user.getLogin());
        }
        return users.get(id-1);
    }
    @PutMapping("/users")
    public User UpdateUser(@RequestBody User user) throws ValidationException {
        if(users.containsKey(user.getId())){
            users.put(user.getId(),
                    new User(user.getId(), user.getEmail(), user.getLogin(), user.getName(), user.getBirthday()));
            log.info("Обновили данные пользователя" + user.getLogin());
            return users.get(id-1);
        }else {
            log.info("Данный пользовтель отсутствует в базе" + user.getLogin());
            throw new ValidationException("Данный пользовтель отсутствует в базе");
        }

    }
}
