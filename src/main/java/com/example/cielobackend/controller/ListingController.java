package com.example.cielobackend.controller;

import com.example.cielobackend.dto.ListingDto;
import com.example.cielobackend.dto.ListingDtoResponse;
import com.example.cielobackend.dto.ListingDtoUpdate;
import com.example.cielobackend.service.ListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cielo/listings")
@RequiredArgsConstructor
public class ListingController {
    private final ListingService listingService;

    @GetMapping("/{id}")
    public ListingDtoResponse getListingById(@PathVariable long id) {
        return listingService.getListingById(id);
    }

    @GetMapping
    public List<ListingDtoResponse> getAllListings() {
        return listingService.getAllListings();
    }

    @PostMapping
    public ListingDtoResponse addListing(@RequestBody @Valid ListingDto listingDto) {
        return listingService.addListing(listingDto);
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
