package com.example.demo.controller;

import com.example.demo.PlayerDto;
import com.example.demo.controller.request.PlayerRequest;
import com.example.demo.controller.request.UpdatePlayerRequest;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Validated
@RequestMapping("/rest/players")
public interface PlayerController {

    @GetMapping
    List<PlayerDto> getPlayerList(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String title,
                                                  @RequestParam(required = false) Race race,
                                                  @RequestParam(required = false) Profession profession,
                                                  @RequestParam(required = false) Date after,
                                                  @RequestParam(required = false) Date before,
                                                  @RequestParam(required = false, defaultValue = "false") boolean banned,
                                                  @RequestParam(required = false) Integer minExperience,
                                                  @RequestParam(required = false) Integer maxExperience,
                                                  @RequestParam(required = false) Integer minLevel,
                                                  @RequestParam(required = false) Integer maxLevel,
                                                  @RequestParam(required = false, defaultValue = "id") PlayerOrder order,
                                                  @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                  @RequestParam(required = false, defaultValue = "3") int pageSize);

    //в какую сущность это лучше засунуть не понимаю как лучше сделать
    ResponseEntity<List<PlayerDto>> getPlayerList(String name, String title, Race race,
                                                  Profession profession, long after, long before,
                                                  boolean banned, int minExperience, int maxExperience,
                                                  int minLevel, int maxLevel, PlayerOrder order,
                                                  int pageNumber, int pageSize);

    @PostMapping
    PlayerDto createNewPlayer(@Valid @RequestBody PlayerRequest createPlayerRequest);

    @GetMapping("/{id}")
    ResponseEntity<PlayerDto> getPlayerById(@PathVariable int id);

    @PostMapping("/{id}")
    ResponseEntity<PlayerDto> updatePlayerById(@PathVariable int id, @RequestBody UpdatePlayerRequest updatePlayerRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deletePlayerById(@PathVariable int id);
}