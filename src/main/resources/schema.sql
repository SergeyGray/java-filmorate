CREATE TABLE IF NOT EXISTS mpa(
                                     id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     mpa varchar (10)
);
CREATE TABLE IF NOT EXISTS films (
                       id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       name varchar(20) not null ,
                       description varchar(200) not null,
                       mpa integer,
                       release_date date,
                       duration integer,
                       FOREIGN KEY (mpa) REFERENCES mpa(id)
);

CREATE TABLE IF NOT EXISTS genres(
                        id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                        genre varchar (20)
);
CREATE TABLE IF NOT EXISTS genres_for_films (
                        id_film integer,
                        id_genre integer,
                        PRIMARY KEY (id_film,id_genre),
                        FOREIGN KEY (id_film) REFERENCES films(id),
                        FOREIGN KEY (id_genre) REFERENCES genres(id)
);
CREATE TABLE IF NOT EXISTS users (
                        id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                        email varchar(256) not null ,
                        login varchar(20) not null ,
                        name varchar(20),
                        birthday date
);
CREATE TABLE IF NOT EXISTS likes (
                        id_film integer,
                        id_user integer,
                        PRIMARY KEY (id_film,id_user),
                        FOREIGN KEY (id_film) REFERENCES films(id),
                        FOREIGN KEY (id_user) REFERENCES users(id)
);
CREATE TABLE IF NOT EXISTS friends (
                                     id integer,
                                     id_friend integer,
                                     status varchar(20),
                                     PRIMARY KEY (id,id_friend),
                                     FOREIGN KEY (id) REFERENCES users(id),
                                     FOREIGN KEY (id_friend) REFERENCES users(id)
);