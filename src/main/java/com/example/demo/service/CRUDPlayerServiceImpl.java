package com.example.demo.service;

import com.example.demo.dao.PlayerDao;
import com.example.demo.dto.PlayerDto;
import com.example.demo.dto.PlayerFilter;
import com.example.demo.entity.Player;
import com.example.demo.exception.PlayerNotFoundException;
import com.example.demo.mapper.PlayerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CRUDPlayerServiceImpl implements CRUDPlayerService {

    private final PlayerDao playerDao;

    @Override
    public List<PlayerDto> getWithFilter(PlayerFilter playerFilter) {
        List<Player> players = playerDao.getWithFilter(playerFilter);
        if (players.isEmpty()) {
            throw new PlayerNotFoundException("Players with such filter are not found.");
        }
        List<PlayerDto> playerDtoList = new ArrayList<>();

        for (Player playerFromList : players) {
            playerDtoList.add(PlayerMapper.mapToPlayerDto(playerFromList));
        }
        return playerDtoList;
    }

    @Override
    public PlayerDto create(PlayerDto player) {
        int level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        int untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
        Player playerToCreate = PlayerMapper.mapToPlayer(player);
        playerToCreate.setLevel(level);
        playerToCreate.setUntilNextLevel(untilNextLevel);
        playerToCreate = playerDao.create(playerToCreate);
        return PlayerMapper.mapToPlayerDto(playerToCreate);
    }

    @Override
    public PlayerDto getById(long id) {
        Player foundPlayer = playerDao.getById(id);
        if (foundPlayer == null) {
            throw new PlayerNotFoundException("Player with id [" + id + "] is not found.");
        }
        return PlayerMapper.mapToPlayerDto(foundPlayer);
    }

    @Override
    public PlayerDto update(PlayerDto player) {
        Player playerToUpdate = PlayerMapper.mapToPlayer(player);
        playerToUpdate = playerDao.update(playerToUpdate);
        if (playerToUpdate == null) {
            throw new PlayerNotFoundException("Player with id [" + player.getId() + "] is not found.");
        }
        return PlayerMapper.mapToPlayerDto(playerToUpdate);
    }

    @Override
    public PlayerDto delete(long id) {
        Player playerToDelete = playerDao.delete(id);
        if (playerToDelete == null) {
            throw new PlayerNotFoundException("Player with id [" + id + "] is not found.");
        }
        return PlayerMapper.mapToPlayerDto(playerToDelete);
    }
}
