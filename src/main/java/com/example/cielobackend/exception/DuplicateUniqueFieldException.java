package com.example.cielobackend.exception;

public class DuplicateUniqueFieldException extends RuntimeException {
    public DuplicateUniqueFieldException(String message) {
        super(message);
    }
}
