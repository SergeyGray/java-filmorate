package ru.yandex.practicum.filmorate.storage.friend;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UsersOnDataBaseException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class FriendDbStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userStorage;

    public void addFriend(int id, int friendId) {
        try {
            String sqlQuery = "INSERT INTO friends(id, id_friend,status) " +
                    "VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlQuery, id, friendId, "Not_accept");
            log.info("Пользователю c id{} добавлен новый друг c id{}", id, friendId);
        } catch (Exception ex) {
            throw new UsersOnDataBaseException(String.format(
                    "Не удалось добавить друга пользователю c id %s", id));
        }
    }

    public void deleteFriend(int id, int friendId) {
        try {
            String sqlQuery = "DELETE  FROM friends  WHERE id = ? AND id_friend = ?";
            jdbcTemplate.update(sqlQuery, id, friendId);
            log.info("У пользователя c id{} удалён друг c id{}", id, friendId);
        } catch (Exception ex) {
            throw new UsersOnDataBaseException(String.format(
                    "Не удалось удалить друга пользователю c id %s", id));
        }
    }

    public List<User> getFriends(int id) {
        try {
            List<User> allFriends = new ArrayList<>();
            SqlRowSet friendsRows = jdbcTemplate.queryForRowSet("select id_friend from friends WHERE id= ?", id);
            while (friendsRows.next()) {
                allFriends.add(userStorage.getUser(friendsRows.getInt("id_friend")));
            }
            return allFriends;
        } catch (EmptyResultDataAccessException ex) {
            throw new UsersOnDataBaseException(String.format(
                    "Не удалось найти пользователя с id %s", id));
        }
    }

    public List<User> getCommonFriends(int id, int otherId) {
        try {
            List<User> allCommonFriends = new ArrayList<>();
            SqlRowSet friendsRows = jdbcTemplate.queryForRowSet(
                    "SELECT f1.id_friend  FROM FRIENDS f1 INNER JOIN " +
                            "friends f2 on f1.id_friend = f2.id_friend WHERE f1.id =? AND f2.id=?", id, otherId);
            while (friendsRows.next()) {
                allCommonFriends.add(userStorage.getUser(friendsRows.getInt("id_friend")));
            }
            log.info("Найдены обзщие друзья у пользователя c id{} и id{}", id, otherId);
            return allCommonFriends;
        } catch (EmptyResultDataAccessException ex) {
            throw new UsersOnDataBaseException("Один из пользователей отсутствует в баззе данных");
        }
    }
}
