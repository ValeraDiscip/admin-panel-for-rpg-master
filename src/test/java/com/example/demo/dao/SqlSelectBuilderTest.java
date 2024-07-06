package com.example.demo.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SqlSelectBuilderTest {
    private final String mainTable = "player";
    private final String columns = "player.id, player.name, title, race.name as race_name, profession.name as profession_name";
    private final String joinRaceTable = "race ON player.race_id = race.id";
    private final String joinProfessionTable = "profession ON player.profession_id = profession.id";


    @Test
    public void buildCorrectSqlColumnsAndMainTableTest() {
        SqlSelectBuilder selectBuilder = new SqlSelectBuilder();

        selectBuilder.columns(columns);
        selectBuilder.mainTable(mainTable);

        String actualSql = selectBuilder.build().getSql();
        String expectedSql = "SELECT player.id, player.name, title, race.name as race_name, profession.name as profession_name FROM player";

        assertEquals(expectedSql, actualSql);
    }

    @Test
    public void buildCorrectSqlColumnsAndMainTableAndJoinTablesAndConditionsTest() {
        SqlSelectBuilder selectBuilder = new SqlSelectBuilder();

        selectBuilder.columns(columns);
        selectBuilder.mainTable(mainTable);
        selectBuilder.joinTables(joinRaceTable, joinProfessionTable);
        selectBuilder.conditions("player.name LIKE ?", "title LIKE ?", "race.name = ?", "profession.name = ?");

        String actualSql = selectBuilder.build().getSql();
        String expectedSql = "SELECT player.id, player.name, title, race.name as race_name, profession.name as profession_name " +
                "FROM player " +
                "JOIN race ON player.race_id = race.id JOIN profession ON player.profession_id = profession.id " +
                "WHERE player.name LIKE ? AND title LIKE ? AND race.name = ? AND profession.name = ?";

        assertEquals(expectedSql, actualSql);
    }

    @Test
    public void buildCorrectSqlColumnsAndMainTableAndJoinTablesAndConditionsAndModifiersTest() {
        SqlSelectBuilder selectBuilder = new SqlSelectBuilder();

        selectBuilder.columns("COUNT(*)");
        selectBuilder.mainTable(mainTable);
        selectBuilder.joinTables(joinRaceTable, joinProfessionTable);
        selectBuilder.conditions("player.name LIKE ?", "title LIKE ?", "race.name = ?", "profession.name = ?");
        selectBuilder.modifiers("ORDER BY ID");
        selectBuilder.modifiers("LIMIT 3");
        selectBuilder.modifiers("OFFSET 0");

        String actualSql = selectBuilder.build().getSql();
        String expectedSql = "SELECT COUNT(*) FROM player " +
                "JOIN race ON player.race_id = race.id JOIN profession ON player.profession_id = profession.id " +
                "WHERE player.name LIKE ? AND title LIKE ? AND race.name = ? AND profession.name = ? " +
                "ORDER BY ID LIMIT 3 OFFSET 0";

        assertEquals(expectedSql, actualSql);
    }

    @Test
    public void buildNoMainTableIncorrectSqlTest() {
        SqlSelectBuilder selectBuilder = new SqlSelectBuilder();

        selectBuilder.columns(columns);
        selectBuilder.joinTables(joinRaceTable, joinProfessionTable);
        selectBuilder.conditions("player.name LIKE ?", "title LIKE ?", "race.name = ?", "profession.name = ?");

        assertThrows(RuntimeException.class, selectBuilder::build);
    }

    @Test
    public void buildNoColumnsIncorrectSqlTest() {
        SqlSelectBuilder selectBuilder = new SqlSelectBuilder();

        selectBuilder.mainTable(mainTable);
        selectBuilder.conditions("player.name LIKE ?", "title LIKE ?", "race.name = ?", "profession.name = ?");

        assertThrows(RuntimeException.class, selectBuilder::build);
    }
}
