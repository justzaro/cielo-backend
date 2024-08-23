package com.example.cielobackend.repository;

import com.example.cielobackend.model.ListingAttribute;
import com.example.cielobackend.model.ListingAttributeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ListingAttributeRepository extends JpaRepository<ListingAttribute, ListingAttributeId> {
    @Transactional
    @Modifying
    @Query(value = """
                   DELETE lav
                   FROM listing_attribute_values AS lav
                   WHERE lav.attribute_id = :attributeId AND lav.listing_id = :listingId
                   """,
           nativeQuery = true
    )
    void deleteAllAttributeValuesForListingAttributePair(@Param("attributeId") long attributeId,
                                                         @Param("listingId") long listingId);

    @Transactional
    @Modifying
    @Query(value = """
                   DELETE la
                   FROM listings_attributes AS la
                   WHERE la.attribute_id = :attributeId AND la.listing_id = :listingId
                   """,
            nativeQuery = true
    )
    void deleteListingAttribute(@Param("attributeId") long attributeId,
                                @Param("listingId") long listingId);
}
