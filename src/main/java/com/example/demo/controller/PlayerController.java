package com.example.demo.controller;

import com.example.demo.dto.PlayerDto;
import com.example.demo.controller.request.PlayerRequest;
import com.example.demo.controller.request.UpdatePlayerRequest;
import com.example.demo.dto.Profession;
import com.example.demo.dto.Race;
import com.example.demo.entity.Player;
import com.example.demo.filter.PlayerOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@Validated
@RequestMapping("/rest/players")
public interface PlayerController {

    @GetMapping
    List<Player> getPlayerList(@RequestParam(required = false) String name,
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
                               @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                               @RequestParam(required = false, defaultValue = "3") Integer pageSize);


    @PostMapping
    PlayerDto createNewPlayer(@Valid @RequestBody PlayerRequest createPlayerRequest);

    @GetMapping("/{id}")
    ResponseEntity<PlayerDto> getPlayerById(@PathVariable long id);

    @PostMapping("/{id}")
    ResponseEntity<PlayerDto> updatePlayerById(@PathVariable long id, @RequestBody UpdatePlayerRequest updatePlayerRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deletePlayerById(@PathVariable long id);
}