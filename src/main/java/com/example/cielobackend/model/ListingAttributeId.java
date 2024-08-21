package com.example.cielobackend.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class ListingAttributeId implements Serializable {
    private Long listingId;
    private Long attributeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingAttributeId that = (ListingAttributeId) o;
        return Objects.equals(listingId, that.listingId) && Objects.equals(attributeId, that.attributeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listingId, attributeId);
    }
}
