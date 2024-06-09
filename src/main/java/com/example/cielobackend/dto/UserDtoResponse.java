package com.example.cielobackend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDtoResponse {
    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private String address;

    private LocalDate dateOfBirth;

    private List<ListingDtoResponse> listings;

    private List<ListingDtoResponse> favouriteListings;
}
