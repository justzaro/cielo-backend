package com.example.cielobackend.repository;

import com.example.cielobackend.model.ListingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingDetailRepository extends JpaRepository<ListingDetail, Long> {
}
