package com.example.cielobackend.service.implementation;

import com.example.cielobackend.dto.ListingDtoResponse;
import com.example.cielobackend.model.Listing;
import com.example.cielobackend.model.ListingImage;
import com.example.cielobackend.repository.ListingImageRepository;
import com.example.cielobackend.service.ListingImageService;
import com.example.cielobackend.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListingImageServiceImpl implements ListingImageService {
    private final ListingService listingService;
    private final ListingImageRepository listingImageRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    @Override
    @CacheEvict(value = "listings", allEntries = true)
    public void addListingImage(long listingId, String imageName) {
        ListingDtoResponse listing = listingService.getListingById(listingId);
        Listing mappedListing = modelMapper.map(listing, Listing.class);
        ListingImage listingImage = new ListingImage();
        listingImage.setName(imageName);
        listingImage.setListing(mappedListing);
        listingImageRepository.save(listingImage);
    }
}
