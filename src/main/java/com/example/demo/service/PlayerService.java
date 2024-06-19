package com.example.demo.service;

import com.example.demo.dto.PlayerDto;
import com.example.demo.dto.PlayerFilter;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface PlayerService {
    List<PlayerDto> getWithFilter(PlayerFilter playerFilter);

    PlayerDto create(PlayerDto player);

    PlayerDto getById(long id);

    PlayerDto update(PlayerDto player);

    PlayerDto delete(long id);
}
