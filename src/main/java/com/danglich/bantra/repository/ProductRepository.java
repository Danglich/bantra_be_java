package com.danglich.bantra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.danglich.bantra.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	Page<Product> findByCategoryId(int categoryId , Pageable page);
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
	Page<Product> findByNameLike( String keyword , Pageable page);
	
	long deleteByCategoryId(int categoryId);
	
	List<Product> findTop10ByOrderBySoldNumberDesc();
	
	@Query("SELECT p FROM Product p WHERE "
			+ "(:keyword IS NULL OR p.name LIKE %:keyword%) AND "
			+ "(:sku IS NULL OR p.sku LIKE %:sku%) AND "
			+ "(:categoryId IS NULL OR p.category.id = :categoryId)")
    Page<Product> findByFilters(String keyword,String sku ,Integer categoryId , Pageable page);
	
	
	@Query("SELECT p FROM Product p WHERE (p.category.id = :categoryId) AND (:keyword IS NULL OR p.name LIKE %:keyword%) AND (:minPrice IS NULL OR p.lowestPrice >= :minPrice) AND (:maxPrice IS NULL OR p.lowestPrice <= :maxPrice)")
	Page<Product> findByCategoryAndFilters(Integer categoryId, String keyword, Float minPrice, Float maxPrice, Pageable page);
	
	
	
}
