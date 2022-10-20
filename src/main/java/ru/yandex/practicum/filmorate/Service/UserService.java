package ru.yandex.practicum.filmorate.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UsersOnMemoryException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(int id, int friendId){
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);
        if(user.getFriends().contains(friendId)){
            throw new UsersOnMemoryException(String.format("У ползователя %s уже имеется в друзьях пользователь %s",
                    user.getLogin(),friend.getLogin()));
        }
        user.getFriends().add(friendId);
        friend.getFriends().add(id);
        log.info("Пользвателю {} добавлен в друзья пользователь {}",
            user.getLogin(),friend.getLogin());
    }

    public void deleteFriend(int id,int friendId){
       User user = userStorage.getUser(id);
       User friend = userStorage.getUser(friendId);
        if(!user.getFriends().contains(friendId)){
            throw new UsersOnMemoryException(String.format("У пользователя %s отсутствует в друзьях пользователь %s",
                    user.getLogin(),friend.getLogin()));
        }
        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
        log.info("У пользвателя {} удален из друзей пользователь {}",
                user.getLogin(),friend.getLogin());
    }
    public Set<User> getFriends(int id){
        return new HashSet<>(userStorage.getAllUsers()
                .stream()
                .filter(user -> user.getFriends().contains(id)).collect(Collectors.toSet()));
    }

    public Set<User> getCommonFriends(int id, int otherId){
        Set<Integer> commonFriends = new HashSet<>(userStorage.getUser(id).getFriends());
        commonFriends.retainAll(userStorage.getUser(otherId).getFriends());
        return userStorage.getAllUsers()
            .stream()
            .filter(user -> commonFriends.contains(user.getId()))
            .collect(Collectors.toSet());
    }
}
