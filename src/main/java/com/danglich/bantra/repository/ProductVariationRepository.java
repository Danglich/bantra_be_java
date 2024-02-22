package com.danglich.bantra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danglich.bantra.model.ProductVariation;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Integer>{

	List<ProductVariation> findByProductId(Integer productId);
}
