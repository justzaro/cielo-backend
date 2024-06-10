package com.example.cielobackend.exception;

public class InvalidOrderByException extends RuntimeException {
    public InvalidOrderByException(String message) {
        super(message);
    }
}
