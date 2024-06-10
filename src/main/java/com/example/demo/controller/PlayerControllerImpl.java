package com.example.demo.controller;

import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.dto.PlayerDto;
import com.example.demo.dto.PlayerFilter;
import com.example.demo.controller.request.PlayerRequest;
import com.example.demo.controller.request.UpdatePlayerRequest;
import com.example.demo.dto.Profession;
import com.example.demo.dto.Race;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.mapper.PlayerMapper;
import com.example.demo.service.CRUDPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//ЗАДАТЬ ВОПРОС ПО ВРЕМЕНИ ПЕРЕДАЧИ В МИЛЛИСЕКУНДАХ И ТД

@RestController
@RequiredArgsConstructor
public class PlayerControllerImpl implements PlayerController {

    private final CRUDPlayerService crudPlayerService;

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

        List<PlayerDto> playerDtoList = crudPlayerService.getWithFilter(playerFilter);

        List<PlayerResponse> responseList = new ArrayList<>();

        for (PlayerDto playerDto : playerDtoList) {
            responseList.add(PlayerMapper.mapToPlayerResponse(playerDto));
        }
        return responseList;
    }

    @Override
    public PlayerResponse createNewPlayer(PlayerRequest createPlayerRequest) {
        PlayerDto playerDto = PlayerMapper.mapToPlayerDto(createPlayerRequest);
        playerDto = crudPlayerService.create(playerDto);
        return PlayerMapper.mapToPlayerResponse(playerDto);
    }

    @Override
    public PlayerResponse getPlayerById(long id) {
        PlayerDto playerDto = crudPlayerService.getById(id);
        return PlayerMapper.mapToPlayerResponse(playerDto);
    }

    @Override
    public PlayerResponse updatePlayerById(long id, UpdatePlayerRequest updatePlayerRequest) {
        PlayerDto playerDto = PlayerMapper.mapToPlayerDto(updatePlayerRequest, id);
        playerDto = crudPlayerService.update(playerDto);
        return PlayerMapper.mapToPlayerResponse(playerDto);
    }

    @Override
    public ResponseEntity<PlayerResponse> deletePlayerById(long id) {
        PlayerDto playerDto = crudPlayerService.delete(id);
        PlayerResponse playerResponse = PlayerMapper.mapToPlayerResponse(playerDto);
        return new ResponseEntity<>(playerResponse, HttpStatus.OK);
    }
}
