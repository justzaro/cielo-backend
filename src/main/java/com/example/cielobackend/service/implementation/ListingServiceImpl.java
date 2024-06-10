package com.example.cielobackend.service.implementation;

import com.example.cielobackend.dto.*;
import com.example.cielobackend.exception.ResourceDoesNotExistException;
import com.example.cielobackend.model.*;
import com.example.cielobackend.repository.*;
import com.example.cielobackend.service.ListingDetailService;
import com.example.cielobackend.service.ListingService;

import com.example.cielobackend.util.PaginationUtils;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.cielobackend.common.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {
    @Value("${rabbitmq.images-exchange.name}")
    private String imagesExchange;
    @Value("${rabbitmq.deleted-images-q.routing-key}")
    private String deletedImagesQueueRoutingKey;
    private final ModelMapper modelMapper;
    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final CategoryRepository categoryRepository;
    private final ListingDetailService listingDetailService;
    private final ListingDetailRepository listingDetailRepository;
    private final ListingDetailValueRepository listingDetailValueRepository;

    @Override
    public Page<ListingDtoResponse> getAllListings(Integer pageNo, Integer limit,
                                                   String sortBy, String orderBy) {
        Pageable pageable = PaginationUtils.createPageable(pageNo, limit, sortBy, orderBy);

        Page<ListingDtoResponse> resultPage = listingRepository
                .findAll(pageable)
                .map(listing -> {
                    ListingDtoResponse dto = modelMapper.map(listing, ListingDtoResponse.class);
                    setSubcategoriesList(dto);
                    setSelectedValuesList(dto);
                    return dto;
                });

        return resultPage.getContent().size() > 0 ? resultPage : Page.empty();
    }

    public ListingDtoResponse getListingById(long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(LISTING_DOES_NOT_EXIST));

        ListingDtoResponse listingResponse = modelMapper.map(listing, ListingDtoResponse.class);
        setSubcategoriesList(listingResponse);
        setSelectedValuesList(listingResponse);
        return listingResponse;
    }

    public Page<ListingDtoResponse> getAllFavouriteListingsForUser(long userId,
                                                                   Integer pageNo, Integer limit,
                                                                   String sortBy, String orderBy) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceDoesNotExistException(USER_DOES_NOT_EXIST));
        System.out.println(user.getFavouriteListings().size());
        Pageable pageable = PaginationUtils.createPageable(pageNo, limit, sortBy, orderBy);

        List<Long> favoriteListingIds = user.getFavouriteListings().stream()
                .map(Listing::getId)
                .collect(Collectors.toList());

        Page<ListingDtoResponse> resultPage = listingRepository
                .findAllByUserAndIdIn(user, favoriteListingIds, pageable)
                .map(listing -> {
                    ListingDtoResponse response = modelMapper.map(listing, ListingDtoResponse.class);
                    setSubcategoriesList(response);
                    setSelectedValuesList(response);
                    return response;
                });

        return resultPage.getContent().size() > 0 ? resultPage : Page.empty();
    }


    @Override
    public Page<ListingDtoResponse> getAllListingsForUser(long userId,
                                                          Integer pageNo, Integer limit,
                                                          String sortBy, String orderBy) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceDoesNotExistException(USER_DOES_NOT_EXIST));

        Pageable pageable = PaginationUtils.createPageable(pageNo, limit, sortBy, orderBy);

        Page<ListingDtoResponse> resultPage = listingRepository
                .findAllByUser(user, pageable)
                .map(listing -> {
                    ListingDtoResponse response = modelMapper.map(listing, ListingDtoResponse.class);
                    setSubcategoriesList(response);
                    setSelectedValuesList(response);
                    return response;
                });

        return resultPage.getContent().size() > 0 ? resultPage : Page.empty();
    }

    @Override
    public void addListingToFavourites(long listingId, long userId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceDoesNotExistException(LISTING_DOES_NOT_EXIST));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceDoesNotExistException(USER_DOES_NOT_EXIST));

        user.getFavouriteListings().add(listing);
        userRepository.save(user);
    }

    private void setSelectedValuesList(ListingDtoResponse listing) {
        List<ListingDetailDtoResponse> details = listing.getDetails();
        for (ListingDetailDtoResponse detail : details) {
            List<Long> ids = new ArrayList<>();
            for (ListingDetailValueDto detailValue : detail.getDetailValues()) {
                ids.add(detailValue.getAttributeValue().getId());
            }
            detail.setSelectedValues(ids);
        }
    }

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
    public ListingDtoResponse addListing(ListingDto listingDto, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceDoesNotExistException(USER_DOES_NOT_EXIST));
        Listing listing = modelMapper.map(listingDto, Listing.class);

        listing.setListedAt(LocalDateTime.now());
        listing.setLastUpdatedAt(LocalDateTime.now());
        listing.setUser(user);

        listing = listingRepository.save(listing);

        setListingDetails(listing, listingDto.getDetails());

        return modelMapper.map(listing, ListingDtoResponse.class);
    }

    @Override
    public ListingDtoResponse updateListing(long id, ListingDtoUpdate listingDto) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(LISTING_DOES_NOT_EXIST));

        handleCategoryChange(listingDto, listing);
        listingDetailService.deleteAllForListing(listing);

        modelMapper.map(listingDto, listing);

        listing.setLastUpdatedAt(LocalDateTime.now());
        setListingDetails(listing, listingDto.getDetails());
        return getListingById(listing.getId());
    }

    private void handleCategoryChange(ListingDtoUpdate listingDto, Listing listing) {
        if (!Objects.equals(listingDto.getCategory().getId(), listing.getCategory().getId())) {
            Category newCategory = categoryRepository.findById(listing.getId())
                    .orElseThrow(() -> new ResourceDoesNotExistException(CATEGORY_DOES_NOT_EXIST));
            listing.setCategory(newCategory);
        }
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
        deleteListingImages(listing.getImages());
        listingRepository.delete(listing);
    }

    @Async
    public void deleteListingImages(List<ListingImage> images) {
        for (ListingImage image : images) {
            rabbitTemplate.convertAndSend(imagesExchange,
                                          deletedImagesQueueRoutingKey,
                                          image.getName());
        }
    }
}