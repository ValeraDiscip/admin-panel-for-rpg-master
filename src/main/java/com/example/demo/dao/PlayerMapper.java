package com.example.demo.dao;

import com.example.demo.dto.Profession;
import com.example.demo.dto.Race;
import com.example.demo.entity.Player;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerMapper implements RowMapper<Player> {
    @Override
    public Player mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Player.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .title(resultSet.getString("title"))
                .race(resultSet.getObject("race", Race.class))
                .profession(resultSet.getObject("profession", Profession.class))
                .birthday(resultSet.getDate("birthday"))
                .banned(resultSet.getBoolean("banned"))
                .experience(resultSet.getInt("experience"))
                .untilNextLevel(resultSet.getInt("untilNextLevel"))
                .build();
    }
}
