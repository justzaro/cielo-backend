package com.example.cielobackend.dto;

import com.example.cielobackend.common.enums.ListingType;
import com.example.cielobackend.common.enums.QualityType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ListingDtoUpdate {
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

    @NotNull
    @DecimalMin("0.01")
    private Double price;

    private Boolean priceIsNegotiable = false;

    private Boolean isActive = true;

    private Boolean isEnabled = true;

    private Boolean isPremium = false;

    private Boolean isAutoRenewable = false;

    private QualityType quality;

    private ListingType type;

    @NotNull
    private CategoryDtoResponse category;

    private List<ListingDetailDto> details;
}
