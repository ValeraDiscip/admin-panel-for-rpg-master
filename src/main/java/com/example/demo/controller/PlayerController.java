package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/players")
public class PlayerController {

    @PostMapping
    public ResponseEntity<CreatePlayerRequest> createNewPlayer(@Valid @RequestBody CreatePlayerRequest createPlayerRequest) {
        return new ResponseEntity<>(createPlayerRequest, HttpStatus.CREATED);
    }
}
