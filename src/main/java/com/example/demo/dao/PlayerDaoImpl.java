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
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlayerDaoImpl implements PlayerDao {
    private final JdbcTemplate jdbcTemplate;
    private final PlayerMapper playerMapper;
    private final String mainTable = "player";
    private final String columns = "player.id, player.name, title, race.name as race_name, profession.name as profession_name, birthday, banned, experience, level, untilNextLevel";
    private final String joinRaceTable = "race ON player.race_id = race.id";
    private final String joinProfessionTable = "profession ON player.profession_id = profession.id";

    @Override
    public List<Player> getWithFilter(PlayerFilter playerFilter) {
        SqlSelectBuilder sqlSelectBuilder = getBaseSqlSelectBuilderFromPlayerFilter(playerFilter);

        sqlSelectBuilder.columns(columns);
        sqlSelectBuilder.mainTable(mainTable);
        sqlSelectBuilder.joinTables(joinRaceTable, joinProfessionTable);
        sqlSelectBuilder.modifiers("ORDER BY " + playerFilter.getOrder());
        sqlSelectBuilder.modifiers("LIMIT " + playerFilter.getPageSize());
        sqlSelectBuilder.modifiers("OFFSET " + playerFilter.getPageNumber() * playerFilter.getPageSize());

        SqlSelectBuilder.SqlSelectBuilderResult result = sqlSelectBuilder.build();

        try {
            return jdbcTemplate.query(result.getSql(), playerMapper, result.getValues());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Integer getCount(PlayerFilter playerFilter) {
        SqlSelectBuilder sqlSelectBuilder = getBaseSqlSelectBuilderFromPlayerFilter(playerFilter);

        sqlSelectBuilder.columns("COUNT(*)");
        sqlSelectBuilder.mainTable(mainTable);

        if (playerFilter.getRace() != null) {
            sqlSelectBuilder.joinTables(joinRaceTable);
        }
        if (playerFilter.getProfession() != null) {
            sqlSelectBuilder.joinTables(joinProfessionTable);
        }

        SqlSelectBuilder.SqlSelectBuilderResult result = sqlSelectBuilder.build();

        try {
            return jdbcTemplate.queryForObject(result.getSql(), Integer.class, result.getValues());
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
        SqlSelectBuilder sqlSelectBuilder = new SqlSelectBuilder();

        sqlSelectBuilder.columns(columns);
        sqlSelectBuilder.mainTable(mainTable);
        sqlSelectBuilder.joinTables(joinRaceTable, joinProfessionTable);
        sqlSelectBuilder.conditions("player.id = ?");

        SqlSelectBuilder.SqlSelectBuilderResult result = sqlSelectBuilder.build();

        try {
            return jdbcTemplate.queryForObject(result.getSql(), playerMapper, id);
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

    private SqlSelectBuilder getBaseSqlSelectBuilderFromPlayerFilter(PlayerFilter playerFilter) {
        SqlSelectBuilder sqlSelectBuilder = new SqlSelectBuilder();

        if (playerFilter.getName() != null) {
            sqlSelectBuilder.conditions("player.name LIKE ?");
            sqlSelectBuilder.values("%" + playerFilter.getName() + "%");
        }
        if (playerFilter.getTitle() != null) {
            sqlSelectBuilder.conditions("title LIKE ?");
            sqlSelectBuilder.values("%" + playerFilter.getTitle() + "%");
        }
        if (playerFilter.getRace() != null) {
            sqlSelectBuilder.conditions("race.name = ?");
            sqlSelectBuilder.values(playerFilter.getRace().name());
        }
        if (playerFilter.getProfession() != null) {
            sqlSelectBuilder.conditions("profession.name = ?");
            sqlSelectBuilder.values(playerFilter.getProfession().name());
        }
        if (playerFilter.getAfter() != null) {
            sqlSelectBuilder.conditions("birthday > ?");
            sqlSelectBuilder.values(playerFilter.getAfter());
        }
        if (playerFilter.getBefore() != null) {
            sqlSelectBuilder.conditions("birthday < ?");
            sqlSelectBuilder.values(playerFilter.getBefore());
        }
        if (playerFilter.getMinExperience() != null) {
            sqlSelectBuilder.conditions("experience >= ?");
            sqlSelectBuilder.values(playerFilter.getMinExperience());
        }
        if (playerFilter.getMaxExperience() != null) {
            sqlSelectBuilder.conditions("experience <= ?");
            sqlSelectBuilder.values(playerFilter.getMaxExperience());
        }
        if (playerFilter.getMinLevel() != null) {
            sqlSelectBuilder.conditions("level >= ?");
            sqlSelectBuilder.values(playerFilter.getMinLevel());
        }
        if (playerFilter.getMaxLevel() != null) {
            sqlSelectBuilder.conditions("level <= ?");
            sqlSelectBuilder.values(playerFilter.getMaxLevel());
        }
        if (playerFilter.getBanned() != null) {
            sqlSelectBuilder.conditions("banned = ?");
            sqlSelectBuilder.values(playerFilter.getBanned());
        }
        return sqlSelectBuilder;
    }
}