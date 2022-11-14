package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.MpaOnDataBaseException;
import ru.yandex.practicum.filmorate.model.Mpa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@AllArgsConstructor
public class MpaDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public Mpa getMpa(int id){
        String sqlQuery = "SELECT * FROM mpa WHERE id=?";
        Mpa mpa;
        try {
            mpa = jdbcTemplate.queryForObject(sqlQuery,this::mapRowToMpa,id);
        }catch (EmptyResultDataAccessException ex){
            throw new MpaOnDataBaseException(String.format("Mpa с id %s не найден",id));
        }
        return mpa;
    }

    public List<Mpa> getAllMpa(){
        String sqlQuery = "SELECT * FROM mpa";
        return jdbcTemplate.query(sqlQuery,this::mapRowToMpa);
    }
    private Mpa mapRowToMpa(ResultSet resultSet,int rowNum)throws  SQLException{
        return Mpa.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("mpa"))
                .build();
    }
}
