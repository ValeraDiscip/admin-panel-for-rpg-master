package com.example.demo.dao;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelectSqlRequestTest {

    @Test
    public void buildCorrectSqlColumnsAndMainTableTest() {
        List<String> columns = new ArrayList<>(Arrays.asList("player.id", "player.name", "title", "race.name as race_name"));

        String actualSqlRequest = SelectSqlRequest.builder().columns(columns).mainTable("player").build().getSql();

        String expectedSqlRequest = "SELECT player.id, player.name, title, race.name as race_name FROM player";

        assertEquals(expectedSqlRequest, actualSqlRequest);
    }

    @Test
    public void buildCorrectSelectSqlRequestTest2() {
        List<String> columns = new ArrayList<>(Arrays.asList("player.id", "player.name", "title", "race.name as race_name",
                "profession.name as profession_name", "birthday", "banned", "experience", "level", "untilNextLevel"));

        List<String> joinTables = new ArrayList<>(Arrays.asList("race ON player.race_id = race.id", "profession ON player.profession_id = profession.id"));

        List<String> modifiers = new ArrayList<>(Arrays.asList("ORDER BY ID", "LIMIT 3", "OFFSET 0"));

        String actualSqlRequest = SelectSqlRequest.builder().columns(columns).mainTable("player")
                .joinTables(joinTables).modifiers(modifiers).build().getSql();

        String expectedSqlRequest = "SELECT player.id, player.name, title, race.name as race_name, " +
                "profession.name as profession_name, birthday, banned, experience, level, untilNextLevel FROM player " +
                "JOIN race ON player.race_id = race.id JOIN profession ON player.profession_id = profession.id ORDER BY ID LIMIT 3 OFFSET 0";

        assertEquals(expectedSqlRequest, actualSqlRequest);
    }

    @Test
    public void buildCorrectSelectSqlRequestTest3() {
        List<String> columns = new ArrayList<>(Arrays.asList("player.id", "player.name", "title", "race.name as race_name",
                "profession.name as profession_name", "birthday", "banned", "experience", "level", "untilNextLevel"));

        List<String> conditions = new ArrayList<>(Arrays.asList("player.name LIKE ?", "title LIKE ?", "race.name = ?",
                "profession.name = ?", "experience >= ?", "experience <= ?", "level >= ?", "level <= ?", "banned = ?"));

        List<String> joinTables = new ArrayList<>(Arrays.asList("race ON player.race_id = race.id", "profession ON player.profession_id = profession.id"));

        List<String> modifiers = new ArrayList<>(Arrays.asList("ORDER BY ID", "LIMIT 3", "OFFSET 0"));

        String actualSqlRequest = SelectSqlRequest.builder().columns(columns).mainTable("player")
                .joinTables(joinTables).conditions(conditions).modifiers(modifiers).build().getSql();

        String expectedSqlRequest = "SELECT player.id, player.name, title, race.name as race_name, " +
                "profession.name as profession_name, birthday, banned, experience, level, untilNextLevel FROM player " +
                "JOIN race ON player.race_id = race.id JOIN profession ON player.profession_id = profession.id " +
                "WHERE player.name LIKE ? AND title LIKE ? AND race.name = ? AND profession.name = ? AND experience >= ? AND experience <= ? AND level >= ? AND level <= ? AND banned = ? " +
                "ORDER BY ID LIMIT 3 OFFSET 0";

        assertEquals(expectedSqlRequest, actualSqlRequest);
    }
}
