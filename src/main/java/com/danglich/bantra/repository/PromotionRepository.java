package com.danglich.bantra.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danglich.bantra.model.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

	@Query("SELECT p FROM Promotion p JOIN p.productCategories pc WHERE "
			+ "(p.endDate >= :currentDate AND p.startDate <= :currentDate) AND "
			+ "(:categoryId IS NULL OR EXISTS (SELECT pc FROM p.productCategories pc WHERE pc.id = :categoryId))")
	Page<Promotion> findActivePromotionsByProductCategoryId(@Param("categoryId") Integer categoryId,
			@Param("currentDate") LocalDateTime currentDate, Pageable page);
	
	
	@Query("SELECT p FROM Promotion p JOIN p.productCategories pc WHERE "
			+ "(:categoryId IS NULL OR EXISTS (SELECT pc FROM p.productCategories pc WHERE pc.id = :categoryId)) AND "
			+ "(p.endDate < :currentDate OR p.startDate > :currentDate)")
	Page<Promotion> findUnActivePromotionsByProductCategoryId(@Param("categoryId") Integer categoryId,
			@Param("currentDate") LocalDateTime currentDate, Pageable page);
	
	@Query("SELECT p FROM Promotion p WHERE "
			+ "(:categoryId IS NULL OR EXISTS (SELECT pc FROM p.productCategories pc WHERE pc.id = :categoryId))" )
	Page<Promotion> findPromotionsByProductCategoryId(@Param("categoryId") Integer categoryId, Pageable page);

}
