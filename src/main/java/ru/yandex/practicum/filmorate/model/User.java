package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.Validator.LoginWithoutSpace;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Slf4j
@AllArgsConstructor
public class User {

   transient private final int id;
   @Email (message = "Неккоректный email")
   @NotBlank (message = "Email не может быть пустым")
    private String email;
   @NotBlank (message =  "Логин не может быть пустым")
   @LoginWithoutSpace
    private String login;
    private String name;
    @PastOrPresent (message = "Дата рождения не может быть в будушем")
    private LocalDate birthday;
    final private Set<Integer> friends = new HashSet<Integer>();
}

