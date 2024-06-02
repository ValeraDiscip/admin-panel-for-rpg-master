package com.example.demo.controller.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BadResponse {
    private String message;
}
