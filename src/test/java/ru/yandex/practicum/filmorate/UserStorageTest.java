package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)

public class UserStorageTest {
    private final UserDbStorage userStorage;
    private User user = User.builder()
            .email("test@Mail.ru")
            .login("testLogin")
            .name("testName")
            .birthday(LocalDate.now())
            .build();
    private User user2 = User.builder()
            .email("test@Mail.ru2")
            .login("testLogin2")
            .name("testName2")
            .birthday(LocalDate.now())
            .build();
    @Test
    public void testUserCreate() {
        userStorage.addUser(user);
        System.out.println(userStorage.getAllUsers());
        Optional<User> userOptional = Optional.ofNullable(userStorage.getUser(1));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1));
    }
    @Test
    public void testUserUpdate() {
        userStorage.addUser(user);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getUser(1));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "testName"));
        user.setName("Update name");
        user.setId(1);
        userStorage.updateUser(user);
        userOptional = Optional.ofNullable(userStorage.getUser(1));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "Update name"));
    }
    @Test
    public void testGetAllUsers() {
        userStorage.addUser(user);
        userStorage.addUser(user2);
        List<User> allUsers= userStorage.getAllUsers();
        assertEquals(allUsers.size(),2);
    }
    @Test
    public void testDeleteUser() {
        userStorage.addUser(user);
        userStorage.addUser(user2);
        userStorage.deleteUser(user);
        List<User> allUsers= userStorage.getAllUsers();
        assertEquals(allUsers.size(),5);
    }
}
