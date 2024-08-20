package com.example.cielobackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class AttributeDtoResponse {
    Long id;
    String name;
    List<AttributeValueDtoResponse> attributeValues;
}
