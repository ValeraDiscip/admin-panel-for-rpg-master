package com.example.demo.mapper;

import com.example.demo.controller.request.PlayerRequest;
import com.example.demo.controller.request.UpdatePlayerRequest;
import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.dto.PlayerDto;
import com.example.demo.dto.PlayerFilter;
import com.example.demo.dto.Profession;
import com.example.demo.dto.Race;
import com.example.demo.entity.Player;
import com.example.demo.filter.PlayerOrder;

import java.util.Date;

public class PlayerMapper {

    public static PlayerDto mapToPlayerDto(PlayerRequest playerRequest) {
        return PlayerDto.builder()
                .name(playerRequest.getName())
                .title(playerRequest.getTitle())
                .race(playerRequest.getRace())
                .profession(playerRequest.getProfession())
                .birthday(playerRequest.getBirthday())
                .banned(playerRequest.isBanned())
                .experience(playerRequest.getExperience())
                .build();
    }


    public static PlayerDto mapToPlayerDto(UpdatePlayerRequest updatePlayerRequest, long id) {
        return PlayerDto.builder()
                .id(id)
                .name(updatePlayerRequest.getName())
                .title(updatePlayerRequest.getTitle())
                .race(updatePlayerRequest.getRace())
                .profession(updatePlayerRequest.getProfession())
                .birthday(updatePlayerRequest.getBirthday())
                .banned(updatePlayerRequest.getBanned())
                .experience(updatePlayerRequest.getExperience())
                .build();
    }

    public static PlayerFilter mapToPlayerFilter(String name, String title, Race race,
                                                 Profession profession, Date after, Date before,
                                                 Boolean banned, Integer minExperience, Integer maxExperience,
                                                 Integer minLevel, Integer maxLevel, PlayerOrder order,
                                                 Integer pageNumber, Integer pageSize) {

        return PlayerFilter.builder()
                .name(name)
                .title(title)
                .race(race)
                .profession(profession)
                .after(after)
                .before(before)
                .banned(banned)
                .minExperience(minExperience)
                .maxExperience(maxExperience)
                .minLevel(minLevel)
                .maxLevel(maxLevel)
                .order(order)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
    }


    public static PlayerDto mapToPlayerDto(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .title(player.getTitle())
                .race(player.getRace())
                .profession(player.getProfession())
                .birthday(player.getBirthday())
                .experience(player.getExperience())
                .level(player.getLevel())
                .untilNextLevel(player.getUntilNextLevel())
                .build();
    }

    public static Player mapToPlayer(PlayerDto playerDto) {

        return Player.builder()
                .id(playerDto.getId())
                .name(playerDto.getName())
                .title(playerDto.getTitle())
                .race(playerDto.getRace())
                .profession(playerDto.getProfession())
                .birthday(playerDto.getBirthday())
                .banned(playerDto.getBanned())
                .experience(playerDto.getExperience())
                .level(playerDto.getLevel())
                .untilNextLevel(playerDto.getUntilNextLevel())
                .build();
    }

    public static PlayerResponse mapToPlayerResponse(PlayerDto playerDto) {
        return PlayerResponse.builder()
                .id(playerDto.getId())
                .name(playerDto.getName())
                .title(playerDto.getTitle())
                .race(playerDto.getRace())
                .profession(playerDto.getProfession())
                .birthday(playerDto.getBirthday().getTime())
                .experience(playerDto.getExperience())
                .level(playerDto.getLevel())
                .untilNextLevel(playerDto.getUntilNextLevel())
                .build();
    }
}
