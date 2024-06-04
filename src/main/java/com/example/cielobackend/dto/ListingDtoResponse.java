package com.example.cielobackend.dto;

import com.example.cielobackend.model.Category;
import com.example.cielobackend.model.ListingDetail;
import com.example.cielobackend.model.ListingImage;
import lombok.Data;

import java.time.LocalDate;
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

    private CategoryDtoResponse category;

    private List<ListingImageDtoResponse> images;

    private List<ListingDetailDtoResponse> listingDetails;
}
