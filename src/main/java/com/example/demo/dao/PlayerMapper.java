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
                .race(Race.valueOf(resultSet.getString("race_name")))
                .profession(Profession.valueOf(resultSet.getString("profession_name")))
                .birthday(resultSet.getDate("birthday"))
                .banned(resultSet.getBoolean("banned"))
                .experience(resultSet.getInt("experience"))
                .level(resultSet.getInt("level"))
                .untilNextLevel(resultSet.getInt("untilNextLevel"))
                .build();
    }
}
