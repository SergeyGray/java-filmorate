package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private UserStorage userStorage;
    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }
    @Autowired
    private FriendDbStorage friendDbStorage;

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUser(int id) {
        return userStorage.getUser(id);
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public void addFriend(int id, int friendId){
        friendDbStorage.addFriend(id,friendId);
    }

    public void deleteFriend(int id,int friendId){
        friendDbStorage.deleteFriend(id,friendId);
    }

    public List<User> getFriends(int id){
        return friendDbStorage.getFriends(id);
    }

    public List<User> getCommonFriends(int id, int otherId) {
        return friendDbStorage.getCommonFriends(id, otherId);
    }
}
