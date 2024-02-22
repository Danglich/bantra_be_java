package com.danglich.bantra.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danglich.bantra.model.Order;
import com.danglich.bantra.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	
	List<Review> findByOrderByIdDesc();
	
	List<Review> findFirst5ByOrderByCreatedAtDesc();
	
	@Query("SELECT r FROM Review r WHERE " +
            "(:startDate IS NULL OR r.createdAt >= :startDate) AND " +
            "(:endDate IS NULL OR r.createdAt <= :endDate) AND " +
            "(:rate IS NULL OR r.rate = :rate)")
    Page<Review> findByFilters(@Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate,
                             @Param("rate") Integer rate,
                             Pageable page);

}
