package com.example.cielobackend.service.implementation;

import com.example.cielobackend.dto.*;
import com.example.cielobackend.exception.ResourceDoesNotExistException;
import com.example.cielobackend.model.*;
import com.example.cielobackend.repository.ListingDetailRepository;
import com.example.cielobackend.repository.ListingDetailValueRepository;
import com.example.cielobackend.repository.ListingRepository;
import com.example.cielobackend.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.cielobackend.common.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {
    private final ListingRepository listingRepository;
    private final ListingDetailRepository listingDetailRepository;
    private final ListingDetailValueRepository listingDetailValueRepository;
    private final AmqpTemplate amqpTemplate;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<ListingDtoResponse> getAllListings() {
        return listingRepository
                .findAll()
                .stream()
                .map(listing -> modelMapper.map(listing, ListingDtoResponse.class))
                .peek(this::setSubcategoriesList)
                //.peek(this::setSelectedValuesList)
                .toList();
    }

    public ListingDtoResponse getListingById(long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(LISTING_DOES_NOT_EXIST));
        ListingDtoResponse listingResponse = modelMapper.map(listing, ListingDtoResponse.class);
        setSubcategoriesList(listingResponse);
        //setSelectedValuesList(listingResponse);
        return listingResponse;
    }

//    private void setSelectedValuesList(ListingDtoResponse listing) {
//        List<ListingDetailDtoResponse> details = listing.getListingDetails();
//        for (ListingDetailDtoResponse detail : details) {
//            List<Long> ids = new ArrayList<>();
//            for (ListingDetailValueDto detailValue : detail.getDetailValues()) {
//                ids.add(detailValue.getAttributeValue().getId());
//            }
//            detail.setSelectedValues(ids);
//        }
//    }


    private void setSubcategoriesList(ListingDtoResponse listing) {
        List<CategoryDtoResponse> categories = new ArrayList<>();
        CategoryDtoResponse category = listing.getCategory();
        category.setSubcategories(null);
        categories.add(category);

        while (category.getParentCategory() != null) {
            category = category.getParentCategory();
            category.setSubcategories(null);
            categories.add(category);
        }

        Collections.reverse(categories);
        for (int i = 0; i < categories.size() - 1; i++) {
            CategoryDtoResponse nextCategory = categories.get(i + 1);
            categories.get(i).setSubcategories(List.of(nextCategory));
        }
        listing.setCategory(categories.get(0));
    }

    @Override
    public ListingDtoResponse addListing(ListingDto listingDto) {
        Listing listing = modelMapper.map(listingDto, Listing.class);

        listing.setListedAt(LocalDateTime.now());
        listing.setLastUpdatedAt(LocalDateTime.now());

        listing = listingRepository.save(listing);

        setListingDetails(listing, listingDto.getDetails());

        return modelMapper.map(listing, ListingDtoResponse.class);
    }

    private void setListingDetails(Listing listing, List<ListingDetailDto> details) {
        for (ListingDetailDto detail : details) {
            ListingDetail listingDetail = new ListingDetail();
            listingDetail.setListing(listing);
            listingDetail.setAttribute(modelMapper.map(detail.getAttribute(), Attribute.class));
            listingDetail = listingDetailRepository.save(listingDetail);

            for (ListingDetailValueDto detailValue : detail.getDetailValues()) {
                ListingDetailValue listingDetailValue = new ListingDetailValue();
                listingDetailValue.setListingDetailValue(listingDetail);
                listingDetailValue.setAttributeValue(modelMapper.map(detailValue.getAttributeValue(), AttributeValue.class));
                listingDetailValueRepository.save(listingDetailValue);
            }
        }
    }

    @Override
    public void deleteListing(long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(LISTING_DOES_NOT_EXIST));
        List<ListingImage> images = listing.getImages();
        for (ListingImage image : images) {
            sendImageToDeletionQueue(image.getName());
        }

        listingRepository.delete(listing);
    }

    private void sendImageToDeletionQueue(String imageName) {
        amqpTemplate.convertAndSend("images-to-be-deleted", imageName);
    }
}