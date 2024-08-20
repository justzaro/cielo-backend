package com.example.cielobackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProvinceCitiesDtoResponse {
    private String name;
    private List<CityDtoResponse> cities;
}
