package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Service.UserService;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
public class UserController {

    private HashMap<Integer, User> users;
    private int id = 1;
    private UserStorage userStorage;
    private UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.users = new HashMap<>();
        this.userStorage = userStorage;
        this.userService = userService;
    }
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Integer id){
        return userStorage.getUser(id);
    }
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userStorage.getAllUsers();
    }
    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        return userStorage.addUser(user);
    }
    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
            return userStorage.updateUser(user);

    }
    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id,@PathVariable int friendId) {
        userService.addFriend(id,friendId);
    }
    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id,@PathVariable int friendId) {
        userService.deleteFriend(id,friendId);
    }
    @GetMapping("/users/{id}/friends")
    public Set<User> getFriends(@PathVariable int id) {
        return userService.getFriends(id);
    }
    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriend(@PathVariable int id,@PathVariable int otherId) {
        return userService.getCommonFriends(id,otherId);
    }
}
