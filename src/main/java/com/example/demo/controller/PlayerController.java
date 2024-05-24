package com.example.demo.controller;

import com.example.demo.PlayerDto;
import com.example.demo.controller.request.GetListOfPlayersRequest;
import com.example.demo.controller.request.PlayerRequest;
import com.example.demo.controller.request.UpdatePlayerRequest;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.service.PlayerDataTransformerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController implements MyController {

//в какую сущность это лучше засунуть не понимаю как лучше сделать
    @Override
    public ResponseEntity<List<PlayerDto>> getPlayerList(String name, String title, Race race,
                                                         Profession profession, long after, long before,
                                                         boolean banned, int minExperience, int maxExperience,
                                                         int minLevel, int maxLevel, PlayerOrder order,
                                                         int pageNumber, int pageSize) {

       GetListOfPlayersRequest getListOfPlayersRequest = PlayerDataTransformerService.transformToGetListOfPlayersRequest(
               name, title, race, profession, after, before, banned, minExperience,
               maxExperience, minLevel, maxLevel, order, pageNumber, pageSize);
        //далее это передается в сервис...
        System.out.println(getListOfPlayersRequest);
        return null;
    }

    @Override
    public ResponseEntity<PlayerDto> createNewPlayer(PlayerRequest createPlayerRequest) {
       PlayerDto playerDto = PlayerDataTransformerService.transformToCreate(createPlayerRequest);
       return new ResponseEntity<>(playerDto, HttpStatus.CREATED);
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
        PlayerDto playerDto = PlayerDataTransformerService.transformToUpdate(updatePlayerRequest);
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
