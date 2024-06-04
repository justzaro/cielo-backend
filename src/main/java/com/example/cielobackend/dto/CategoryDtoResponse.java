package com.example.cielobackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CategoryDtoResponse {
    private Long id;

    private String name;

    private String imageName;

    private Integer level;

    @JsonIgnore
    private CategoryDtoResponse parentCategory;

    private List<CategoryDtoResponse> subcategories;

    private Set<AttributeDtoResponse> attributes;
}
