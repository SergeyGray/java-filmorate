package ru.yandex.practicum.filmorate.storage.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UsersOnDataBaseException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User getUser(int id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where id = ?", id);
        if (userRows.next()) {
            return createUserFromSql(userRows);
        } else
            throw new UsersOnDataBaseException(String.format(" Пользователь с id %s отсутствует в базе данных", id));
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users");
        while (userRows.next()) {
            allUsers.add(createUserFromSql(userRows));
        }
        return allUsers;
    }

    @Override
    public User addUser(User user) {
        String sqlQuery = "INSERT INTO users (email, login, name, birthday) " +
                "VALUES (?, ?, ?, ?)";
        int result = jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), validationUserName(user), user.getBirthday());
        if (result > 0) {
            log.info("Добавлен новый пользователь: {}", user.getLogin());
            SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT id FROM users ORDER BY id desc LIMIT 1;");
            if (userRows.next()) {
                user.setId(userRows.getInt("id"));
            }
            return user;
        } else
            throw new UsersOnDataBaseException(String.format(
                    "Не удалось добавить пользователя %s в базу данных", user.getLogin()));
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "UPDATE users SET " +
                "email = ?, login = ?, name = ?, birthday = ? " +
                "WHERE id = ?";
        int result = jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                validationUserName(user),
                user.getBirthday(),
                user.getId());
        if (result > 0) {
            log.info("Данные пользователя {} обновлены", user.getLogin());
            return user;
        } else {
            throw new UsersOnDataBaseException(String.format(
                    "Не удалось обновить данные пользователя %s в базе данных", user.getLogin()));
        }
    }

    @Override
    public void deleteUser(User user) {
        int result = jdbcTemplate.update("DELETE FROM users WHERE  id = ?", user.getId());
        if (result > 0) {
            log.info("Удалили пользователя из базы данных: {}", user.getName());
        } else {
            throw new UsersOnDataBaseException(String.format("Пользователь %s отсутствует в базе данных", user.getLogin()));
        }
    }

    private String validationUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user.getName();
    }

    private User createUserFromSql(SqlRowSet userRows) {
        return User.builder()
                .id(userRows.getInt("id"))
                .email(userRows.getString("email"))
                .login(userRows.getString("login"))
                .name(userRows.getString("name"))
                .birthday(userRows.getDate("birthday").toLocalDate())
                .build();
    }
}
