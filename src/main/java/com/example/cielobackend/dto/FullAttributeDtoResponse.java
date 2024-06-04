package com.example.cielobackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class FullAttributeDtoResponse {
    private Long id;
    private String name;
    private List<AttributeValueDtoResponse> attributeValues;
}
