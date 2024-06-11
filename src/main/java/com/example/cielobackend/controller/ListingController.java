package com.example.cielobackend.controller;

import com.example.cielobackend.dto.ListingDto;
import com.example.cielobackend.dto.ListingDtoResponse;
import com.example.cielobackend.dto.ListingDtoUpdate;
import com.example.cielobackend.service.ListingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.basePath}/listings")
@RequiredArgsConstructor
public class ListingController {
    private final ListingService listingService;

    @GetMapping("/{id}")
    public ListingDtoResponse getListingById(@PathVariable long id) {
        return listingService.getListingById(id);
    }

    @GetMapping
    public Page<ListingDtoResponse> getListings(
            @RequestParam(value = "pageNo", defaultValue = "1") @Min(1) Integer pageNo,
            @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(20)  Integer limit,
            @RequestParam(value = "sortBy", defaultValue = "listedAt") String sortBy,
            @RequestParam(value = "orderBy", defaultValue = "asc") String orderBy) {
        return listingService.getListings(pageNo, limit, sortBy, orderBy);
    }

    @PostMapping
    public ListingDtoResponse addListing(@RequestBody @Valid ListingDto listingDto,
                                         @RequestParam("userId") long userId) {
        return listingService.addListing(listingDto, userId);
    }

    @PutMapping("/{id}")
    public ListingDtoResponse updateListing(@PathVariable long id,
                                            @RequestBody @Valid ListingDtoUpdate listingDto) {
        return listingService.updateListing(id, listingDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable long id) {
        listingService.deleteListing(id);
        return ResponseEntity
               .noContent()
               .build();
    }
}
