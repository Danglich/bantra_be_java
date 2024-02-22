package com.danglich.bantra.service;

import java.util.List;

import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.ProductDto;
import com.danglich.bantra.dto.ProductRequest;
import com.danglich.bantra.model.Product;

public interface ProductService {

	// create 
	Product createProduct(int categoryId, ProductRequest request);
	
	// get all
	PaginationResponse getAll(String keyword, String sku ,Integer categoryId ,int pageNumber, String filter);
	
	// get by category
	PaginationResponse getByCategory(Integer categoryId ,String keyword ,int pageNumber, String filter, Float minPrice, Float maxPrice);
	
	// delete by id
	void deleteById(int productId);
	
	// delete by category
	long deleteByCategory(int categoryId);
	
	// update 
	Product update(Product productRequest);
	
	// Get by id
	Product getById(int productId);
	
	// Get best selling product
	List<ProductDto> getTopProduct();
	
	//List<ProductDto> getReviewableProducts();
	

}
