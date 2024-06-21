package com.example.cielobackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "listing_details")
public class ListingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_detail_id")
    private Long id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private Listing listing;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    @OneToMany(mappedBy = "listingDetail", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ListingDetailValue> detailValues;
}
