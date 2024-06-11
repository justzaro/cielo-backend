package com.example.cielobackend.repository;

import com.example.cielobackend.model.Category;
import com.example.cielobackend.model.Listing;
import com.example.cielobackend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {
    Page<Listing> findAllByUser(User user, Pageable pageable);
    Page<Listing> findAllByUserAndIdIn(User user, List<Long> listingIds, Pageable pageable);

}
