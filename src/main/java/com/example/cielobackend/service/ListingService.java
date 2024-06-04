package com.example.cielobackend.service;

import com.example.cielobackend.dto.ListingDto;
import com.example.cielobackend.dto.ListingDtoResponse;

import java.util.List;

public interface ListingService {
    ListingDtoResponse addListing(ListingDto listingDto);
    List<ListingDtoResponse> getAllListings();
    ListingDtoResponse getListingById(long id);
    void deleteListing(long id);
}
