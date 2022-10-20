package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User getUser(int id);
    List<User> getAllUsers();
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
}
