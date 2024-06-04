package com.example.cielobackend.exception;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    @Positive
    private int status;

    @NotBlank
    private String message;

    private LocalDateTime timestamp;
}
