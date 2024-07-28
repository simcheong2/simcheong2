package com.example.simcheong2.global.exception.model;

import lombok.Data;

@Data
public class CommonExceptionResponse {
    private final String code;
    private final String message;
}
