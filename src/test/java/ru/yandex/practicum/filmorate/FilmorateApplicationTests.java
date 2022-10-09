package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FilmorateApplication.class)
class FilmorateApplicationTests {

	@Test
	void createUserLoginWithSpace() throws ValidationException {
		Throwable thrown = assertThrows(ValidationException.class,()->{
			User user = new User(1,"bla@mail.ru","bla bla","blabla", LocalDate.now());
		});
		assertNotNull(thrown.getMessage());
	}
	@Test
	void createUserWrongEmail() throws ValidationException {
		Throwable thrown = assertThrows(ValidationException.class,()->{
			User user = new User(1,"blamail.ru","blabla","blabla", LocalDate.now());
		});
		assertNotNull(thrown.getMessage());
	}	@Test
	void createUserBlankEmail() throws ValidationException {
		Throwable thrown = assertThrows(ValidationException.class,()->{
			User user = new User(1,"","blabla","blabla", LocalDate.now());
		});
		assertNotNull(thrown.getMessage());
	}
	@Test
	void createUserNullEmail() throws ValidationException {
		Throwable thrown = assertThrows(ValidationException.class,()->{
			User user = new User(1,null,"blabla","blabla", LocalDate.now());
		});
		assertNotNull(thrown.getMessage());
	}
	@Test
	void createUserNullName() throws ValidationException {
			User user = new User(1,"bla@mail.ru","blabla",null, LocalDate.now());
		assertEquals(user.getName(),user.getLogin());
	}
	@Test
	void createUserBirthdayOnFuture() throws ValidationException {
		Throwable thrown = assertThrows(ValidationException.class,()->{
			User user = new User(1,"bla@mail.ru","blabla","blabla", LocalDate.of(2030,10,10));
		});
		assertNotNull(thrown.getMessage());
	}
	@Test
	void createFilmBlankName() throws ValidationException {
		Throwable thrown = assertThrows(ValidationException.class,()->{
			Film film = new Film(1,"","blabla",LocalDate.of(2002,10,10), 100);
		});
		assertNotNull(thrown.getMessage());
	}
	@Test
	void createFilmNullName() throws ValidationException {
		Throwable thrown = assertThrows(ValidationException.class,()->{
			Film film = new Film(1,null,"blabla",LocalDate.of(2002,10,10), 100);
		});
		assertNotNull(thrown.getMessage());
	}
	@Test
	void createFilmTooLongDescription() throws ValidationException {
		String description = "Россия - священная наша держава,\n" +
				"Россия - любимая наша страна.\n" +
				"Могучая воля, великая слава -\n" +
				"Твое достоянье на все времена!\n" +
				"Славься, Отечество наше свободное,\n" +
				"Братских народов союз вековой,\n" +
				"Предками данная мудрость народная!\n";
		Throwable thrown = assertThrows(ValidationException.class,()->{
			Film film = new Film(1,"bla",description,LocalDate.of(2002,10,10), 100);
		});
		assertNotNull(thrown.getMessage());
	}
	@Test
	void createFilmTooEarleReleaseDate() throws ValidationException {
		Throwable thrown = assertThrows(ValidationException.class,()->{
			Film film = new Film(1,"bla","blabla",LocalDate.of(1700,10,10), 100);
		});
		assertNotNull(thrown.getMessage());
	}
	@Test
	void createFilmNegativeDuration() throws ValidationException {
		Throwable thrown = assertThrows(ValidationException.class,()->{
			Film film = new Film(1,"bla","blabla",LocalDate.of(2002,10,10), -5);
		});
		assertNotNull(thrown.getMessage());
	}
	@Test
	void createFilmZeroDuration() throws ValidationException {
		Throwable thrown = assertThrows(ValidationException.class,()->{
			Film film = new Film(1,"bla","blabla",LocalDate.of(2002,10,10), 0);
		});
		assertNotNull(thrown.getMessage());
	}
}
