package com.example.demo.exception;

import com.example.demo.controller.response.BadResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadResponse> handleException(MethodArgumentNotValidException ex) {
        BadResponse badResponse = BadResponse.builder()
                .message(ex.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(" ")))
                .build();
        return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<BadResponse> handleException(PlayerNotFoundException ex) {
        BadResponse badResponse = BadResponse.builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(badResponse, HttpStatus.NOT_FOUND);
    }
}
