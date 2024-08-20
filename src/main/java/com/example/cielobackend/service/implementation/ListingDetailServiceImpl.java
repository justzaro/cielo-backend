package com.example.cielobackend.service.implementation;

import com.example.cielobackend.model.Listing;
import com.example.cielobackend.repository.ListingDetailRepository;
import com.example.cielobackend.service.ListingDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListingDetailServiceImpl implements ListingDetailService {
    private final ListingDetailRepository listingDetailRepository;

    @Override
    public void deleteAllForListing(Listing listing) {
        listingDetailRepository.deleteAll(listing.getDetails());
    }
}
