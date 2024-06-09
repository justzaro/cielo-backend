package com.example.cielobackend.service;

import com.example.cielobackend.dto.ListingDto;
import com.example.cielobackend.dto.ListingDtoResponse;
import com.example.cielobackend.dto.ListingDtoUpdate;
import com.example.cielobackend.model.Category;
import com.example.cielobackend.model.ListingImage;

import java.util.List;

public interface ListingService {
    ListingDtoResponse addListing(ListingDto listingDto);
    ListingDtoResponse updateListing(long id, ListingDtoUpdate listingDto);
    List<ListingDtoResponse> getAllListings();
    ListingDtoResponse getListingById(long id);
    void deleteListing(long id);
    void deleteListingImages(List<ListingImage> images);
}
