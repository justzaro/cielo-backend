package com.example.cielobackend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CategoryDto {
    @NotBlank
    @Size(min = 2, max = 50, message = "Category name should be between 2 and 50 characters long!")
    private String name;

    private String imageName = "/default";

    private CategoryDtoResponse parentCategory;

    @PositiveOrZero(message = "Level value can't be negative!")
    private int level;
}
