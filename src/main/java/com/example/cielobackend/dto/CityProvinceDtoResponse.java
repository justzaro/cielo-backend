package com.example.cielobackend.dto;

import lombok.Data;

@Data
public class CityProvinceDtoResponse {
    private String name;
    private ProvinceDtoResponse province;
}
