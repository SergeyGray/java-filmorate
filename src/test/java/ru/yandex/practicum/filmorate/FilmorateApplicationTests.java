package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = FilmorateApplication.class)
class FilmorateApplicationTests {

	@Test
	void contextLoads() throws ValidationException {
		User user = new User(1,"bla@mail.ru","bla","blabla", LocalDate.now());
		assertEquals(user.getId(),1);
	}
}
