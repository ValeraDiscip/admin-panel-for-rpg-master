package com.example.demo.dao;

import com.example.demo.dto.PlayerFilter;
import com.example.demo.entity.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PlayerDaoImpl implements PlayerDao {
    private final JdbcTemplate jdbcTemplate;

    //задать вопрос оп страницам мне кажется по умолчанию pagenumber должен быть 1 - так будет правильнее
    //а если вообще никакие данные на вход не пришли или такого не будет все будет там настроено во фронте?
    @Override
    public List<Player> getWithFilter(PlayerFilter playerFilter) {
        StringBuilder sql = new StringBuilder("SELECT player.id, player.name, title, " +
                "race.name as race_name, profession.name as profession_name, birthday, banned, " +
                "experience, level, untilNextLevel FROM player JOIN race ON player.race_id = race.id " +
                "JOIN profession ON player.profession_id = profession.id");

        List<Object> params = new ArrayList<>();

        ArrayList<String> clauses = new ArrayList<>();

        Integer offSet = playerFilter.getPageNumber() * playerFilter.getPageSize() - playerFilter.getPageSize();

        if (playerFilter.getName() != null) {
            clauses.add("player.name LIKE ?");
            params.add("%" + playerFilter.getName() + "%");
        }
        if (playerFilter.getTitle() != null) {
            clauses.add("title LIKE ?");
            params.add("%" + playerFilter.getTitle() + "%");
        }
        if (playerFilter.getRace() != null) {
            clauses.add("race.name = ?");
            params.add(playerFilter.getRace().name());
        }
        if (playerFilter.getProfession() != null) {
            clauses.add("profession.name = ?");
            params.add(playerFilter.getProfession().name());
        }
        if (playerFilter.getAfter() != null) {
            clauses.add("birthday > ?");
            params.add(playerFilter.getAfter());
        }
        if (playerFilter.getBefore() != null) {
            clauses.add("birthday < ?");
            params.add(playerFilter.getBefore());
        }
        if (playerFilter.getMinExperience() != null) {
            clauses.add("experience >= ?");
            params.add(playerFilter.getMinExperience());
        }
        if (playerFilter.getMaxExperience() != null) {
            clauses.add("experience <= ?");
            params.add(playerFilter.getMaxExperience());
        }
        if (playerFilter.getMinLevel() != null) {
            clauses.add("level >= ?");
            params.add(playerFilter.getMinLevel());
        }
        if (playerFilter.getMaxLevel() != null) {
            clauses.add("level <= ?");
            params.add(playerFilter.getMaxLevel());
        }
        if (playerFilter.getBanned() != null) {
            clauses.add("banned = ?");
            params.add(playerFilter.getBanned());
        }
        if (!clauses.isEmpty()) {
            sql.append(" WHERE ");
            String clausesString = String.join(" and ", clauses);
            sql.append(clausesString);
        }

        sql.append(" ORDER BY " + playerFilter.getOrder());
        sql.append(" OFFSET " + offSet);
        sql.append(" LIMIT " + playerFilter.getPageSize());

        try {
            return jdbcTemplate.query(sql.toString(), new PlayerMapper(), params.toArray());
        } catch (EmptyResultDataAccessException e) {
            return null;
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

        return getById((Long) keyHolder.getKey());

    }

    @Override
    public Player getById(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT player.id, player.name, title, race.name as race_name, profession.name as profession_name, birthday, banned, experience, level, untilNextLevel " +
                    "FROM player JOIN race ON player.race_id = race.id JOIN profession ON player.profession_id = profession.id WHERE player.id = ?", new PlayerMapper(), id);
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
}