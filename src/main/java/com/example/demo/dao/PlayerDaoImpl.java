package com.example.demo.dao;

import com.example.demo.dto.PlayerFilter;
import com.example.demo.entity.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlayerDaoImpl implements PlayerDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Player> getWithFilter(PlayerFilter playerFilter) {
        SqlSelectBuilder sqlSelectBuilder = getBaseSqlSelectBuilderFromPlayerFilter(playerFilter);

        List<String> columns = new ArrayList<>(Arrays.asList("player.id", "player.name", "title", "race.name as race_name",
                "profession.name as profession_name", "birthday", "banned", "experience", "level", "untilNextLevel"));

        List<String> joinTables = new ArrayList<>(Arrays.asList("race ON player.race_id = race.id", "profession ON player.profession_id = profession.id"));

        List<String> modifiers = new ArrayList<>(Arrays.asList(
                "ORDER BY " + playerFilter.getOrder(),
                "LIMIT " + playerFilter.getPageSize(),
                "OFFSET " + playerFilter.getPageNumber() * playerFilter.getPageSize()));

        SqlSelectBuilder.SqlSelectBuilderResult result = sqlSelectBuilder.build();

        try {
            return jdbcTemplate.query(result.getSql(), new PlayerMapper(), result.getValues());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Integer getCount(PlayerFilter playerFilter) {
        List<String> columns = new ArrayList<>(Arrays.asList("COUNT(*)"));

        List<String> joinTables = new ArrayList<>();

        if (playerFilter.getRace() != null) {
            joinTables.add("race ON player.race_id = race.id");
        }
        if (playerFilter.getProfession() != null) {
            joinTables.add("profession ON player.profession_id = profession.id");
        }

        SelectSqlRequest selectSqlRequest = getBaseSqlSelectBuilderFromPlayerFilter(playerFilter, columns, joinTables, "player", null);

        try {
            return jdbcTemplate.queryForObject(selectSqlRequest.getSql(), Integer.class, selectSqlRequest.getValues().toArray());
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public Player create(Player player) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String insertPlayerSql = "INSERT INTO player (name, title, race_id, profession_id, birthday, banned, experience, level, untilnextlevel)\n" +
                "VALUES  (?, ?, (Select id from race where race.name = ?), " +
                "(Select id from profession where profession.name = ?), ?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(insertPlayerSql, new String[]{"id"});
            ps.setString(1, player.getName());
            ps.setString(2, player.getTitle());
            ps.setString(3, player.getRace().name());
            ps.setString(4, player.getProfession().name());
            Date date = new Date(player.getBirthday().getTime());
            ps.setDate(5, date);
            ps.setBoolean(6, player.getBanned());
            ps.setInt(7, player.getExperience());
            ps.setInt(8, player.getLevel());
            ps.setInt(9, player.getUntilNextLevel());
            return ps;
        }, keyHolder);
        //написано что может быть nullpointerexception что делать в таком случае отлавливать и что вернуть? как будет правильнее

        return getById((Long) keyHolder.getKey());
    }

    @Override
    public Player getById(long id) {
        List<String> columns = new ArrayList<>(Arrays.asList("player.id", "player.name", "title", "race.name as race_name",
                "profession.name as profession_name", "birthday", "banned", "experience", "level", "untilNextLevel"));

        List<String> joinTables = new ArrayList<>(Arrays.asList("race ON player.race_id = race.id", "profession ON player.profession_id = profession.id"));

        String sql = SelectSqlRequest.builder().columns(columns).mainTable("player").joinTables(joinTables)
                .conditions(new ArrayList<>(Arrays.asList("player.id = ?"))).build().getSql();
        try {
            return jdbcTemplate.queryForObject(sql, new PlayerMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Player update(Player player) {

        Player playerForUpdate = getById(player.getId());
        if (playerForUpdate == null) {
            return null;
        }
        if (player.getName() != null) {
            playerForUpdate.setName(player.getName());
        }
        if (player.getTitle() != null) {
            playerForUpdate.setTitle(player.getTitle());
        }
        if (player.getRace() != null) {
            playerForUpdate.setRace(player.getRace());
        }
        if (player.getProfession() != null) {
            playerForUpdate.setProfession(player.getProfession());
        }
        if (player.getBirthday() != null) {
            playerForUpdate.setBirthday(player.getBirthday());
        }
        if (player.getBanned() != null) {
            playerForUpdate.setBanned(player.getBanned());
        }
        if (player.getExperience() != null) {
            playerForUpdate.setExperience(player.getExperience());
        }
        jdbcTemplate.update("UPDATE player SET name = ?, title = ?, race_id = (Select id from race where race.name = ?), " +
                        "profession_id = (Select id from profession where profession.name = ?), birthday = ?, banned = ?, experience = ? WHERE player.id = ?",
                playerForUpdate.getName(), playerForUpdate.getTitle(), playerForUpdate.getRace().name(), playerForUpdate.getProfession().name(),
                playerForUpdate.getBirthday(), playerForUpdate.getBanned(), playerForUpdate.getExperience(), playerForUpdate.getId());

        return playerForUpdate;
    }

    @Override
    public Player delete(long id) {
        Player playerForDelete = getById(id);
        if (playerForDelete == null) {
            return null;
        }
        jdbcTemplate.update("DELETE FROM player WHERE id = ?", id);
        return playerForDelete;
    }

    //НЕ ЗАБЫТЬ ПРЕДЛОЖИТЬ ИДЕЮ ПО ИЗМЕНЕНИЮ ЭТОГО МЕТОДА ТАК КАК ЕГО ЗАДАЧА ВСЕ ЖЕ НЕ В ПОСТРОЕНИИ ЗАПРОСА А В ФОРМИРОВАНИИ УСЛОВИЙ И ЗНАЧЕНИЙ
    private SqlSelectBuilder getBaseSqlSelectBuilderFromPlayerFilter(PlayerFilter playerFilter) {
        SqlSelectBuilder sqlSelectBuilder = new SqlSelectBuilder();

        if (playerFilter.getName() != null) {
            sqlSelectBuilder.condition("player.name LIKE ?");
            //values.add("%" + playerFilter.getName() + "%");
        }
//        if (playerFilter.getTitle() != null) {
//            conditions.add("title LIKE ?");
//            values.add("%" + playerFilter.getTitle() + "%");
//        }
//        if (playerFilter.getRace() != null) {
//            conditions.add("race.name = ?");
//            values.add(playerFilter.getRace().name());
//        }
//        if (playerFilter.getProfession() != null) {
//            conditions.add("profession.name = ?");
//            values.add(playerFilter.getProfession().name());
//        }
//        if (playerFilter.getAfter() != null) {
//            conditions.add("birthday > ?");
//            values.add(playerFilter.getAfter());
//        }
//        if (playerFilter.getBefore() != null) {
//            conditions.add("birthday < ?");
//            values.add(playerFilter.getBefore());
//        }
//        if (playerFilter.getMinExperience() != null) {
//            conditions.add("experience >= ?");
//            values.add(playerFilter.getMinExperience());
//        }
//        if (playerFilter.getMaxExperience() != null) {
//            conditions.add("experience <= ?");
//            values.add(playerFilter.getMaxExperience());
//        }
//        if (playerFilter.getMinLevel() != null) {
//            conditions.add("level >= ?");
//            values.add(playerFilter.getMinLevel());
//        }
//        if (playerFilter.getMaxLevel() != null) {
//            conditions.add("level <= ?");
//            values.add(playerFilter.getMaxLevel());
//        }
//        if (playerFilter.getBanned() != null) {
//            conditions.add("banned = ?");
//            values.add(playerFilter.getBanned());
//        }
        return sqlSelectBuilder;
    }
}