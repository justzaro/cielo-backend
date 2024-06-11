package com.example.cielobackend.service;

import com.example.cielobackend.dto.ListingDto;
import com.example.cielobackend.dto.ListingDtoResponse;
import com.example.cielobackend.dto.ListingDtoUpdate;
import com.example.cielobackend.model.ListingImage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ListingService {
    ListingDtoResponse addListing(ListingDto listingDto, long userId);
    ListingDtoResponse updateListing(long id, ListingDtoUpdate listingDto);
    Page<ListingDtoResponse> getListings(Integer pageNo, Integer limit,
                                         String sortBy, String orderBy);
    void addListingToFavourites(long listingId, long userId);
    Page<ListingDtoResponse> getAllFavouriteListingsForUser(long userId,
                                                            Integer pageNo, Integer limit,
                                                            String sortBy, String orderBy);
    Page<ListingDtoResponse> getAllListingsByUser(long userId,
                                                  Integer pageNo, Integer limit,
                                                  String sortBy, String orderBy);
    ListingDtoResponse getListingById(long id);
    void deleteListing(long id);
    void deleteListingImages(List<ListingImage> images);
}
