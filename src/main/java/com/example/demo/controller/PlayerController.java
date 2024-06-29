package com.example.demo.controller;

import com.example.demo.controller.request.PlayerRequest;
import com.example.demo.controller.request.UpdatePlayerRequest;
import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.dto.Profession;
import com.example.demo.dto.Race;
import com.example.demo.filter.PlayerOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;

@Validated
@RequestMapping("/rest/players")
public interface PlayerController {
    /*
        Нужно изменить сигнатуру методов, которые использую фильтр. Сейчас мы принимаем параметры, как отдельные переменные, а надо сделать так, чтобы отдельные параметры собирались в ОДИН объект,
        примерно как в методе createNewPlayer, НО учесть, что в том методе мы собираем из ТЕЛА запроса, а в нашем случае собирать надо НЕ ИЗ ТЕЛА, а из ПАРАМЕТРОВ
     */

    @GetMapping
    List<PlayerResponse> getPlayerList(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String title,
                                       @RequestParam(required = false) Race race,
                                       @RequestParam(required = false) Profession profession,
                                       @RequestParam(required = false) Date after,
                                       @RequestParam(required = false) Date before,
                                       @RequestParam(required = false) Boolean banned,
                                       @RequestParam(required = false) Integer minExperience,
                                       @RequestParam(required = false) Integer maxExperience,
                                       @RequestParam(required = false) Integer minLevel,
                                       @RequestParam(required = false) Integer maxLevel,
                                       @RequestParam(required = false, defaultValue = "ID") PlayerOrder order,
                                       @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                       @RequestParam(required = false, defaultValue = "3") Integer pageSize);

    @GetMapping("/count")
    Integer getPlayerCount(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String title,
                           @RequestParam(required = false) Race race,
                           @RequestParam(required = false) Profession profession,
                           @RequestParam(required = false) Date after,
                           @RequestParam(required = false) Date before,
                           @RequestParam(required = false) Boolean banned,
                           @RequestParam(required = false) Integer minExperience,
                           @RequestParam(required = false) Integer maxExperience,
                           @RequestParam(required = false) Integer minLevel,
                           @RequestParam(required = false) Integer maxLevel);


    @PostMapping
    PlayerResponse createNewPlayer(@Valid @RequestBody PlayerRequest createPlayerRequest);

    @GetMapping("/{id}")
    ResponseEntity<PlayerResponse> getPlayerById(@PositiveOrZero @PathVariable long id);

    @PostMapping("/{id}")
    ResponseEntity<PlayerResponse> updatePlayerById(@PositiveOrZero @PathVariable long id, @RequestBody UpdatePlayerRequest updatePlayerRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deletePlayerById(@PositiveOrZero @PathVariable long id);
}