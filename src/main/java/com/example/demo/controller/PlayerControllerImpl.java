package com.example.demo.controller;

import com.example.demo.controller.request.PlayerRequest;
import com.example.demo.controller.request.UpdatePlayerRequest;
import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.dto.PlayerDto;
import com.example.demo.dto.PlayerFilter;
import com.example.demo.dto.Profession;
import com.example.demo.dto.Race;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.mapper.PlayerMapper;
import com.example.demo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayerControllerImpl implements PlayerController {
    private final PlayerService crudPlayerService;

    @Override
    public List<PlayerResponse> getPlayerList(String name,
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

        List<PlayerDto> foundPlayerList = crudPlayerService.getWithFilter(playerFilter);

        return foundPlayerList.stream()
                .map(PlayerMapper::mapToPlayerResponse)
                .toList();
    }

    @Override
    public PlayerResponse createNewPlayer(PlayerRequest createPlayerRequest) {
        PlayerDto playerForCreate = PlayerMapper.mapToPlayerDto(createPlayerRequest);
        PlayerDto createdPlayer = crudPlayerService.create(playerForCreate);
        return PlayerMapper.mapToPlayerResponse(createdPlayer);
    }

    @Override
    public ResponseEntity<PlayerResponse> getPlayerById(long id) {
        PlayerDto foundPlayer = crudPlayerService.getById(id);
        if (foundPlayer == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(PlayerMapper.mapToPlayerResponse(foundPlayer));
    }

    @Override
    public ResponseEntity<PlayerResponse> updatePlayerById(long id, UpdatePlayerRequest updatePlayerRequest) {
        PlayerDto playerForUpdate = PlayerMapper.mapToPlayerDto(updatePlayerRequest, id);
        PlayerDto updatedPlayer = crudPlayerService.update(playerForUpdate);
        if (updatedPlayer == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(PlayerMapper.mapToPlayerResponse(updatedPlayer));
    }

    @Override
    public ResponseEntity<HttpStatus> deletePlayerById(long id) {
        PlayerDto deletedPlayer = crudPlayerService.delete(id);
        if (deletedPlayer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
