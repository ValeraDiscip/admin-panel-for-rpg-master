package com.example.demo.dao;

import com.example.demo.dto.Profession;
import com.example.demo.dto.Race;
import com.example.demo.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
//СПРОСИТЬ ПРО JDBC ДРАЙВЕР
@Component
public class PlayerDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlayerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createPlayer(Player player) {
        jdbcTemplate.update("INSERT INTO player VALUES (1,?,?,?,?,?,?,?,?,?)", player.getName(),
                player.getTitle(), player.getRace(), player.getProfession(), player.getBirthday(),
                player.getBanned(), player.getExperience(), player.getLevel(), player.getUntilNextLevel());
    }
    //в бд же устанавливается id то есть я должен обратиться еще в бд и достать оттуда player чтобы вернуть

    //айди же формируется бд как здесь вставку пропустить айди?

    //задать вопрос про обновление игрока

    public Player getPlayerById(long id) {

        return jdbcTemplate.query("SELECT * FROM player WHERE id = ?", new BeanPropertyRowMapper<>(Player.class), id)
                .stream().findFirst().orElse(null);
    }
    //СПРОСИТЬ что возвращать если игрок не найден создать свой собственный exception с сообщением и отлавливать его в глобальном обработчике

    public Player updatePlayer(Player player, long id) {
        if ((getPlayerById(id)) == null) {
            return null;
        } else {
            return checkForNullAndUpdatePlayerFields(player, id);
        }
    }
    //опять же есть вопрос про возврат данных


    public void deletePlayer(long id) {
        //здесь по идее тоже надо сначала проверить если ли такой игрок вообще в бд
        //опять же вопрос тогда как и что лучше возвращать
        jdbcTemplate.update("DELETE FROM player WHERE id = ?", id);
    }


    private Player checkForNullAndUpdatePlayerFields(Player player, long id) {
        if (player.getName() != null) {
            updateName(player.getName(), id);
        }
        if (player.getTitle() != null) {
            updateTitle(player.getTitle(), id);
        }
        if (player.getRace() != null) {
            updateRace(player.getRace(), id);
        }
        if (player.getProfession() != null) {
            updateProfession(player.getProfession(), id);
        }
        if (player.getBirthday() != null) {
            updateBirthday(player.getBirthday(), id);
        }
        if (player.getBanned() != null) {
            updateBanned(player.getBanned(), id);
        }
        if (player.getExperience() != null) {
            updateExperience(player.getExperience(), id);
        }
        return getPlayerById(id);
    }

    private void updateName(String name, long id) {
        jdbcTemplate.update("UPDATE player SET name = ? WHERE id = ?", name, id);
    }

    private void updateTitle(String title, long id) {
        jdbcTemplate.update("UPDATE player SET title = ? WHERE id = ?", title, id);
    }

    private void updateRace(Race race, long id) {
        jdbcTemplate.update("UPDATE player SET race = ? WHERE id = ?", race, id);
    }

    private void updateProfession(Profession profession, long id) {
        jdbcTemplate.update("UPDATE player SET profession = ? WHERE id = ?", profession, id);
    }

    private void updateBirthday(Date birthday, long id) {
        jdbcTemplate.update("UPDATE player SET birthday = ? WHERE id = ?", birthday, id);
    }

    private void updateBanned(boolean banned, long id) {
        jdbcTemplate.update("UPDATE player SET banned = ? WHERE id = ?", banned, id);
    }

    private void updateExperience(int experience, long id) {
        jdbcTemplate.update("Update player SET experience = ? WHERE id = ?", experience, id);
    }
}
