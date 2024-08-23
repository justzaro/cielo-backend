package com.example.cielobackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
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
                    @JoinColumn(name = "listing_id", referencedColumnName = "listing_id"),
                    @JoinColumn(name = "attribute_id", referencedColumnName = "attribute_id")
            },
            inverseJoinColumns = @JoinColumn(name = "attribute_value_id")
    )
    private Set<AttributeValue> attributeValues;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingAttribute that = (ListingAttribute) o;
        return Objects.equals(listingAttributeId, that.listingAttributeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listingAttributeId);
    }
}
