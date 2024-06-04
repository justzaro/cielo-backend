package com.example.cielobackend.repository;

import com.example.cielobackend.model.ListingDetailValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingDetailValueRepository extends JpaRepository<ListingDetailValue, Long> {
}
