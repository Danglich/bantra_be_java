package com.danglich.bantra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danglich.bantra.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>{

	Optional<ProductCategory> findBySlug(String slug);
}
