package com.example.demo.service;

import com.example.demo.PlayerDto;
import com.example.demo.controller.request.GetListOfPlayersRequest;
import com.example.demo.controller.request.PlayerRequest;
import com.example.demo.controller.request.UpdatePlayerRequest;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerOrder;

//как лучше поступить переименовать класс этот на TransformerService так как один метод не подходит по названию в этот класс
// ЛИБО создать отдельный сервис ради этого метода типа такой вопрос!
public abstract class PlayerDataTransformerService {

    public static PlayerDto transformToCreate(PlayerRequest playerRequest) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(playerRequest.getName());
        playerDto.setTitle(playerRequest.getTitle());
        playerDto.setRace(playerRequest.getRace());
        playerDto.setProfession(playerRequest.getProfession());
        playerDto.setBirthday(playerRequest.getBirthday());
        playerDto.setBanned(playerRequest.isBanned());
        playerDto.setExperience(playerRequest.getExperience());
        return playerDto;
    }

    public static PlayerDto transformToUpdate(UpdatePlayerRequest updatePlayerRequest) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(updatePlayerRequest.getName());
        playerDto.setTitle(updatePlayerRequest.getTitle());
        playerDto.setRace(updatePlayerRequest.getRace());
        playerDto.setProfession(updatePlayerRequest.getProfession());
        playerDto.setBirthday(updatePlayerRequest.getBirthday());
        playerDto.setBanned(updatePlayerRequest.isBanned());
        playerDto.setExperience(updatePlayerRequest.getExperience());
        return playerDto;
    }

    public static GetListOfPlayersRequest transformToGetListOfPlayersRequest(String name, String title, Race race,
                                                                             Profession profession, long after, long before,
                                                                             boolean banned, int minExperience, int maxExperience,
                                                                             int minLevel, int maxLevel, PlayerOrder order,
                                                                             int pageNumber, int pageSize) {
        GetListOfPlayersRequest request = new GetListOfPlayersRequest();
        request.setName(name);
        request.setTitle(title);
        request.setRace(race);
        request.setProfession(profession);
        request.setAfter(after);
        request.setBefore(before);
        request.setBanned(banned);
        request.setMinExperience(minExperience);
        request.setMaxExperience(maxExperience);
        request.setMinLevel(minLevel);
        request.setMaxLevel(maxLevel);
        if (order != null) {
            order = PlayerOrder.ID;
        }
        request.setOrder(order);
        request.setPageNumber(pageNumber);
        if (pageSize == 0) {
            pageSize = 3;
        }
        request.setPageSize(pageSize);
        return request;
    }
}
