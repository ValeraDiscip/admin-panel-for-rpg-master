package com.example.demo.service;

import com.example.demo.dto.PlayerDto;
import com.example.demo.dto.PlayerFilter;

import java.util.List;

public interface PlayerService {
    List<PlayerDto> getWithFilter(PlayerFilter playerFilter);

    Integer getCount(PlayerFilter playerFilter);

    PlayerDto create(PlayerDto player);

    PlayerDto getById(long id);

    PlayerDto update(PlayerDto player);

    PlayerDto delete(long id);
}
