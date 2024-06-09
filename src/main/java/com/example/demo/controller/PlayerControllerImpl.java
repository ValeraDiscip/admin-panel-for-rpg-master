package com.example.demo.controller;

import com.example.demo.dto.PlayerDto;
import com.example.demo.dto.PlayerFilter;
import com.example.demo.controller.request.PlayerRequest;
import com.example.demo.controller.request.UpdatePlayerRequest;
import com.example.demo.dto.Profession;
import com.example.demo.dto.Race;
import com.example.demo.entity.Player;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.service.PlayerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class PlayerControllerImpl implements PlayerController {

    //в какую сущность это лучше засунуть не понимаю как лучше сделать
    @Override
    public List<Player> getPlayerList(String name,
                                      String title,
                                      Race race,
                                      Profession profession,
                                      Date after,
                                      Date before,
                                      Boolean banned,
                                      Integer minExperience,
                                      Integer maxExperience,
                                      Integer minLevel,
                                      Integer maxLevel,
                                      PlayerOrder order,
                                      Integer pageNumber,
                                      Integer pageSize) {

        PlayerFilter playerFilter = PlayerMapper.mapToPlayerFilter(
                name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel, order, pageNumber, pageSize);
        //далее это передается в сервис...
        System.out.println(playerFilter);
        return null;
    }

    @Override
    public PlayerDto createNewPlayer(PlayerRequest createPlayerRequest) {
        PlayerDto playerDto = PlayerMapper.mapToPlayerDto(createPlayerRequest);
        return playerDto;
    }

    @Override
    public ResponseEntity<PlayerDto> getPlayerById(long id) {
        return null;
    }

    @Override
    public ResponseEntity<PlayerDto> updatePlayerById(long id, UpdatePlayerRequest updatePlayerRequest) {
        PlayerDto playerDto = PlayerMapper.mapToPlayerDto(updatePlayerRequest, id);
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> deletePlayerById(long id) {
        return null;
    }
}
