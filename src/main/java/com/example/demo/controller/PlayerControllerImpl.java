package com.example.demo.controller;

import com.example.demo.dto.PlayerDto;
import com.example.demo.dto.PlayerFilter;
import com.example.demo.controller.request.PlayerRequest;
import com.example.demo.controller.request.UpdatePlayerRequest;
import com.example.demo.dto.Profession;
import com.example.demo.dto.Race;
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
    public List<PlayerDto> getPlayerList(String name,
                                         String title,
                                         Race race,
                                         Profession profession,
                                         Date after,
                                         Date before,
                                         boolean banned,
                                         Integer minExperience,
                                         Integer maxExperience,
                                         Integer minLevel,
                                         Integer maxLevel,
                                         PlayerOrder order,
                                         int pageNumber,
                                         int pageSize) {

       PlayerFilter playerFilter = PlayerMapper.mapToFilter(
               name, title, race, profession, after, before, banned, minExperience,
               maxExperience, minLevel, maxLevel, order, pageNumber, pageSize);
        //далее это передается в сервис...
        System.out.println(playerFilter);
        return null;
    }

    @Override
    public PlayerDto createNewPlayer(PlayerRequest createPlayerRequest) {
       PlayerDto playerDto = PlayerMapper.transformToCreate(createPlayerRequest);
       return playerDto;
    }

    @Override
    public ResponseEntity<PlayerDto> getPlayerById(int id) {
        /*здесь трансформировать ничего не нужно, то есть этот id просто передается в сервис а сервис его передает
        далее вплоть до базы данных так?(только если id валидный конечно же)
        как будто проще здесь сразу обратиться к DAO
        */
        return null;
    }

    @Override
    public ResponseEntity<PlayerDto> updatePlayerById(int id, UpdatePlayerRequest updatePlayerRequest) {
        PlayerDto playerDto = PlayerMapper.transformToUpdate(updatePlayerRequest);
        /*далее передается в сервис который ищет в базе данных игрока с таким айди и если он находится все поля которые не нулл
        обновляются с этого playerDto в player в db и возвращаются админу.

         если айди валидный, то трансформировать в playerDto, после  айди со всей информацией передается в сервис \
        и если игрок с таким айди найден то обновляется он в базе данных
        и приходит ответ админу обновленный player?
        */

        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> deletePlayerById(int id) {
        return null;
    }
}
