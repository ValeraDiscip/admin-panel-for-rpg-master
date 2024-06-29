package com.example.demo.exception;

import com.example.demo.controller.response.BadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadResponse> handleException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException ", ex);
        List<String> messages = ex.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return createBadResponse(messages);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BadResponse> handleException(ConstraintViolationException ex) {
        List<String> messages = new ArrayList<>();
        log.error("ConstraintViolationException ", ex);
        messages.add(ex.getMessage());
        return createBadResponse(messages);
    }

    private ResponseEntity<BadResponse> createBadResponse(List<String> message) {
        BadResponse badResponse = BadResponse.builder()
                .messages(message)
                .build();

        return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
    }
}
