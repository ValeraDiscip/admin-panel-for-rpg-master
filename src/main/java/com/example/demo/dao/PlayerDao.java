package com.example.demo.dao;

import com.example.demo.dto.PlayerFilter;
import com.example.demo.entity.Player;

import java.util.List;

public interface PlayerDao {
    List<Player> getWithFilter(PlayerFilter playerFilter);
    Integer getCount(PlayerFilter playerFilter);
    Player create(Player player);
    Player getById(long id);
    Player update(Player player);
    Player delete(long id);
}
