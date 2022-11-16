package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UsersOnMemoryException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> users;
    private int id;

    public InMemoryUserStorage() {
        users = new HashMap<>();
        id = 1;
    }

    @Override
    public User getUser(int id) {
        if (!users.containsKey(id)) {
            throw new UsersOnMemoryException(String.format(" Пользователь с id %s отсутствует в памяти", id));
        }
        return users.get(id);
    }

    @Override
    public List<User> getAllUsers() {
        return users.values().stream().collect(Collectors.toList());
    }

    @Override
    public User addUser(User user) {
        if (users.containsValue(user)) {
            throw new UsersOnMemoryException(String.format("Пользователь %s уже присутствует в памяти", user.getLogin()));
        }
        users.put(id, User.builder()
                .id(id)
                .email(user.getEmail())
                .login(user.getEmail())
                .name(user.getName())
                .birthday(user.getBirthday())
                .build());
        ++id;
        log.info("Добавлен новый пользователь: {}", user.getLogin());
        return users.get(id - 1);
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), User.builder()
                    .id(id)
                    .email(user.getEmail())
                    .login(user.getEmail())
                    .name(user.getName())
                    .birthday(user.getBirthday())
                    .build());
            log.info("Обновили данные для пользователя: {}", user.getLogin());
            return users.get(id - 1);
        } else {
            throw new UsersOnMemoryException(String.format("Пользователь %s отсутствует в памяти", user.getLogin()));
        }
    }

    @Override
    public void deleteUser(User user) {
        if (users.containsKey(user.getId())) {
            users.remove(user);
            log.info("Удалили пользователя из памяти: {}", user.getName());
        } else {
            throw new UsersOnMemoryException(String.format("Пользователь %s отсутствует в памяти", user.getLogin()));
        }
    }

    private String validationUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            return user.getLogin();
        } else {
            return user.getName();
        }
    }
}
