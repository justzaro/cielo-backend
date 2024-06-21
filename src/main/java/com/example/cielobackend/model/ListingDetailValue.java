package com.example.cielobackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "listing_details_values")
@Data
public class ListingDetailValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_detail_value_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "listing_detail_id")
    private ListingDetail listingDetail;

    @ManyToOne
    @JoinColumn(name = "attribute_value_id")
    private AttributeValue attributeValue;
}
