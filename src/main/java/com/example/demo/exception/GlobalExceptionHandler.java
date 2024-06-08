package com.example.demo.exception;

import com.example.demo.controller.response.BadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // как правильно возвращать ответ нормальный пользователю с ошибкой
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadResponse> handleException(MethodArgumentNotValidException ex) {
        BadResponse badResponse = BadResponse.builder()
                .message(ex.getFieldError().getDefaultMessage())
                .build();
        return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
    }
}
