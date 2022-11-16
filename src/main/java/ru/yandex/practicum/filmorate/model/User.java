package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.LoginWithoutSpace;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {

    private final Set<Integer> friends = new HashSet();
    private int id;
    @Email(message = "Неккоректный email")
    @NotBlank(message = "Email не может быть пустым")
    private String email;
    @NotBlank(message = "Логин не может быть пустым")
    @LoginWithoutSpace
    private String login;
    private String name;
    @PastOrPresent(message = "Дата рождения не может быть в будушем")
    private LocalDate birthday;
}

