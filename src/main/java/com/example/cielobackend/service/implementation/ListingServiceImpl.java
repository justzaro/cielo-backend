package com.example.cielobackend.service.implementation;

import com.example.cielobackend.dto.*;
import com.example.cielobackend.exception.ResourceDoesNotExistException;
import com.example.cielobackend.model.*;
import com.example.cielobackend.repository.*;
import com.example.cielobackend.service.ListingService;

import com.example.cielobackend.pagination.AbstractSpecification;
import com.example.cielobackend.pagination.PaginationUtils;
import com.example.cielobackend.pagination.SpecificationFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Attr;

import java.time.LocalDateTime;
import java.util.*;
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
    private final ListingAttributeRepository listingAttributeRepository;
    private final SpecificationFactory specificationFactory;

    @Override
    public Page<ListingDtoResponse> getListings(Map<String, String[]> params,
                                                int categoryId, int page, int limit,
                                                String sortBy, String orderBy) {
        return getListingDtoResponsePage(params, categoryId, page, limit, sortBy, orderBy);
    }

    @NotNull
    private Page<ListingDtoResponse> getListingDtoResponsePage(Map<String, String[]> params,
                                                               int categoryId, int page, int limit,
                                                               String sortBy, String orderBy) {
        Page<Listing> listingPage = getListingPage(categoryId, page, limit, sortBy, orderBy, params);

        Page<ListingDtoResponse> dtoResultPage = listingPage.map(listing -> {
            ListingDtoResponse dto = modelMapper.map(listing, ListingDtoResponse.class);
            setSubcategoriesList(dto);
            //setSelectedValuesList(dto);
            return dto;
        });

        return dtoResultPage.getContent().size() > 0 ? dtoResultPage : Page.empty();
    }

    private Page<Listing> getListingPage(int categoryId, int page, int limit,
                                         String sortBy, String orderBy,
                                         Map<String, String[]> params) {
        Pageable pageable = PaginationUtils.createPageable(page, limit, sortBy, orderBy);
        List<Integer> childCategoriesIds = categoryRepository.findAllChildCategories(categoryId);
        String categoryName = categoryRepository.findRootCategoryOfGivenCategoryId(categoryId);

        AbstractSpecification<Listing> specification = specificationFactory.getSpecification(categoryName, params, childCategoriesIds);
        return listingRepository.findAll(specification, pageable);
    }

    public ListingDtoResponse getListingById(long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(LISTING_DOES_NOT_EXIST));

        ListingDtoResponse listingResponse = modelMapper.map(listing, ListingDtoResponse.class);
        setSubcategoriesList(listingResponse);
//        setSelectedValuesList(listingResponse);

        return listingResponse;
    }

    public Page<ListingDtoResponse> getAllFavouriteListingsForUser(long userId,
                                                                   int page, int limit,
                                                                   String sortBy, String orderBy) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceDoesNotExistException(USER_DOES_NOT_EXIST));

        Pageable pageable = PaginationUtils.createPageable(page, limit, sortBy, orderBy);

        List<Long> favoriteListingIds = user.getFavouriteListings()
                                            .stream()
                                            .map(Listing::getId)
                                            .collect(Collectors.toList());

        Page<ListingDtoResponse> resultPage = listingRepository
                .findAllByUserAndIdIn(user, favoriteListingIds, pageable)
                .map(listing -> {
                    ListingDtoResponse response = modelMapper.map(listing, ListingDtoResponse.class);
                    setSubcategoriesList(response);
                    //setSelectedValuesList(response);
                    return response;
                });

        return resultPage.getContent().size() > 0 ? resultPage : Page.empty();
    }


    @Override
    public Page<ListingDtoResponse> getAllListingsByUser(long userId,
                                                         int categoryId, int page, int limit,
                                                         String sortBy, String orderBy) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceDoesNotExistException(USER_DOES_NOT_EXIST);
        }

        Map<String, String[]> params = new HashMap<>();
        params.put("userId", new String[]{String.valueOf(userId)});

        return getListingDtoResponsePage(params, categoryId, page, limit, sortBy, orderBy);
    }

    public List<ListingAttributeDto> getAllAttributesForListing(long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(LISTING_DOES_NOT_EXIST));

        return listing.getAttributes()
                .stream()
                .map(listingAttribute -> modelMapper.map(listingAttribute, ListingAttributeDto.class))
                .collect(Collectors.toList());
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

        setListingAttributes(listing, listingDto.getAttributes());

        return getListingById(listing.getId());
    }

    @Override
    public ListingDtoResponse updateListing(long id, ListingDto listingDto) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(LISTING_DOES_NOT_EXIST));

        handleCategoryChange(listingDto, listing);
        modelMapper.map(listingDto, listing);
        setListingAttributes(listing, listingDto.getAttributes());

        return getListingById(id);
    }

    private void handleCategoryChange(ListingDto listingDto, Listing listing) {
        if (!Objects.equals(listingDto.getCategory().getId(), listing.getCategory().getId())) {
            Category newCategory = categoryRepository.findById(listingDto.getCategory().getId())
                    .orElseThrow(() -> new ResourceDoesNotExistException(CATEGORY_DOES_NOT_EXIST));
            listing.setCategory(newCategory);
        }
    }

    private void setListingAttributes(Listing listing, List<ListingAttributeDto> attributes) {
        for (ListingAttributeDto listingAttributeDto : attributes) {
            ListingAttribute listingAttribute = new ListingAttribute();

            ListingAttributeId listingAttributeId = new ListingAttributeId();
            listingAttributeId.setListingId(listing.getId());
            listingAttributeId.setAttributeId(listingAttributeDto.getAttribute().getId());

            listingAttribute.setListingAttributeId(listingAttributeId);
            listingAttribute.setListing(listing);
            listingAttribute.setAttribute(modelMapper.map(listingAttributeDto.getAttribute(), Attribute.class));
            listingAttribute.setValue(listingAttributeDto.getValue());
            listingAttribute.setAttributeValues(new HashSet<>());

            listingAttributeRepository.save(listingAttribute);

            Set<AttributeValue> attributeValues = new HashSet<>();
            for (AttributeValueDtoResponse attributeValueDto : listingAttributeDto.getAttribute().getAttributeValues()) {
                AttributeValue attributeValue = modelMapper.map(attributeValueDto, AttributeValue.class);
                attributeValues.add(attributeValue);
            }
            listingAttribute.setAttributeValues(attributeValues);

            listingAttributeRepository.save(listingAttribute);
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