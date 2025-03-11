package com.Pharmacy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Pharmacy.model.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    @Query("SELECT r FROM Pharmacy r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%',:query,'%')) OR LOWER(r.madeIn) LIKE LOWER(CONCAT('%',:query,'%'))")
    List<Pharmacy> findBySearchQuery(@Param("query") String query);

    Pharmacy findByOwnerId(Long userId);  
}