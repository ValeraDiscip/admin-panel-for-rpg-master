package com.example.demo.service;

import com.example.demo.dao.PlayerDao;
import com.example.demo.dto.PlayerDto;
import com.example.demo.dto.PlayerFilter;
import com.example.demo.entity.Player;
import com.example.demo.mapper.PlayerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerDao playerDao;

    @Override
    public List<PlayerDto> getWithFilter(PlayerFilter playerFilter) {
        List<Player> foundPlayerList = playerDao.getWithFilter(playerFilter);

        return foundPlayerList.stream()
                .map(PlayerMapper::mapToPlayerDto)
                .toList();
    }

    @Override
    public PlayerDto create(PlayerDto player) {
        int level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        int untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
        Player playerForCreate = PlayerMapper.mapToPlayer(player);
        playerForCreate.setLevel(level);
        playerForCreate.setUntilNextLevel(untilNextLevel);
        Player createdPlayer = playerDao.create(playerForCreate);
        return PlayerMapper.mapToPlayerDto(createdPlayer);
    }

    @Override
    public PlayerDto getById(long id) {
        Player foundPlayer = playerDao.getById(id);
        if (foundPlayer == null) {
            return null;
        }
        log.info("Found player with id [{}].", id);
        return PlayerMapper.mapToPlayerDto(foundPlayer);
    }

    @Override
    public PlayerDto update(PlayerDto player) {
        Player playerForUpdate = PlayerMapper.mapToPlayer(player);
        Player updatedPlayer = playerDao.update(playerForUpdate);
        if (updatedPlayer == null) {
            return null;
        }
        return PlayerMapper.mapToPlayerDto(updatedPlayer);
    }

    @Override
    public PlayerDto delete(long id) {
        Player playerForDelete = playerDao.delete(id);
        if (playerForDelete == null) {
            return null;
        }
        return PlayerMapper.mapToPlayerDto(playerForDelete);
    }
}

