package com.example.cielobackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "contact_phone", nullable = false)
    private String contactPhone;

    @Column(name = "contact_name", nullable = false)
    private String contactName;

    @Column(name = "price_is_negotiable")
    private Boolean priceIsNegotiable;

    private Double price;

    @Column(name = "favourites_counter")
    private Long favouritesCounter;

    @Column(name = "views_counter")
    private Long viewsCounter;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "listed_on", nullable = false)
    private LocalDateTime listedAt;

    @Column(name = "last_updated_on")
    private LocalDateTime lastUpdatedAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "is_premium", nullable = false)
    private Boolean isPremium;

    @Column(name = "is_auto_renewable", nullable = false)
    private Boolean isAutoRenewable;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<ListingDetail> listingDetails;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<ListingImage> images;
}
