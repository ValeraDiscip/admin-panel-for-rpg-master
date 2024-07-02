package com.example.demo.controller;

import com.example.demo.controller.request.PlayerRequest;
import com.example.demo.controller.request.UpdatePlayerRequest;
import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.dto.PlayerFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RequestMapping("/rest/players")
public interface PlayerController {

    @GetMapping
    List<PlayerResponse> getPlayerList(PlayerFilter playerFilter);

    @GetMapping("/count")
    Integer getPlayerCount(PlayerFilter playerFilter);


    @PostMapping
    PlayerResponse createNewPlayer(@Valid @RequestBody PlayerRequest createPlayerRequest);

    @GetMapping("/{id}")
    ResponseEntity<PlayerResponse> getPlayerById(@PositiveOrZero @PathVariable long id);

    @PostMapping("/{id}")
    ResponseEntity<PlayerResponse> updatePlayerById(@PositiveOrZero @PathVariable long id, @RequestBody UpdatePlayerRequest updatePlayerRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deletePlayerById(@PositiveOrZero @PathVariable long id);
}