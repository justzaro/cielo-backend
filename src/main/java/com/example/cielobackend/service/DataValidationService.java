package com.example.cielobackend.service;

public interface DataValidationService {
    <T> T getResourceOrThrow(Class<T> entityClass, long id);
    <T> T getResourceOrThrow(Class<T> entityClass, long id, String errorMessage);
}
