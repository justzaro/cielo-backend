package com.example.cielobackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "listings_attributes")
public class ListingAttribute {
    @EmbeddedId
    private ListingAttributeId listingAttributeId;

    @ManyToOne
    @MapsId("listingId")
    @JoinColumn(name = "listing_id")
    private Listing listing;

    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    private String value;

    @ManyToMany
    @JoinTable(
            name = "listing_attribute_values",
            joinColumns = {
                    @JoinColumn(name = "listing_id"),
                    @JoinColumn(name = "attribute_id")
            },
            inverseJoinColumns = @JoinColumn(name = "attribute_value_id")
    )
    private Set<AttributeValue> attributeValues;
}
