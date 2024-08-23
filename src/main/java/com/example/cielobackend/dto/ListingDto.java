package com.example.cielobackend.dto;

import com.example.cielobackend.common.enums.ListingType;
import com.example.cielobackend.common.enums.QualityType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ListingDto {
    @NotBlank
    @Size(min = 5, max = 200, message = "Title length should be between 5 and 50 characters!")
    private String title;

    @NotBlank
    @Size(min = 5, max = 200, message = "Description length should be between 5 and 50 characters!")
    private String description;

    @NotBlank
    @Size(min = 5, max = 20, message = "Phone number should be between 5 and 50 characters!")
    private String contactPhone;

    @NotBlank
    @Size(min = 1, max = 50, message = "Contact name should be between 5 and 50 characters!")
    private String contactName;

    @DecimalMin("0.01")
    private Double price;

    private Boolean priceIsNegotiable = false;

    private Boolean isActive = true;

    private Boolean isEnabled = true;

    private Boolean isPremium = false;

    private Boolean isAutoRenewable = false;

    private ListingType type;

    private QualityType quality;

    @NotNull
    private CategoryDtoResponse category;

    private List<ListingAttributeDto> attributes;
}
