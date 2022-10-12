package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
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
    public User addUser(@Valid @RequestBody User user) throws UsersOnMemoryException {
        if(users.containsValue(user)){
            throw new UsersOnMemoryException("Данный пользователь уже присутствует в памяти ");
        }
        users.put(id,new User(id,user.getEmail(), user.getLogin(), validationUserName(user), user.getBirthday()));
        ++id;
        log.info("Добавлен новый пользователь " + user.getLogin());
        return users.get(id-1);
    }
    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) throws UsersOnMemoryException {
        if(users.containsKey(user.getId())){
            users.put(user.getId(),
                    new User(user.getId(),user.getEmail(), user.getLogin(), user.getName(), user.getBirthday()));
            log.info("Обновили данные пользователя" + user.getLogin());
            return users.get(id-1);
        }else {
            throw new UsersOnMemoryException("Данный пользователь отсутствует в памяти");
        }
    }
    private String validationUserName(User user){
        if (user.getName() == null|| user.getName().isBlank()){
            return user.getLogin();
        } else{
            return user.getName();
        }
    }
}
