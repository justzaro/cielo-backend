package com.example.cielobackend.dto;

import com.example.cielobackend.common.enums.ListingType;
import com.example.cielobackend.common.enums.QualityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ListingDtoResponse {
    private Long id;

    private String title;

    private String description;

    private String contactPhone;

    private String contactName;

    private Boolean priceIsNegotiable;

    private Double price;

    private Long favouritesCounter;

    private Long viewsCounter;

    private String imagePath;

    private LocalDateTime listedAt;

    private LocalDateTime lastUpdatedAt;

    private Boolean isActive;

    private Boolean isEnabled;

    private Boolean isPremium;

    private Boolean isAutoRenewable;

    private QualityType quality;

    private ListingType type;

    private CityProvinceDtoResponse city;

    private CategoryDtoResponse category;

    private List<ListingImageDtoResponse> images;

    private List<ListingDetailDtoResponse> details;
}
