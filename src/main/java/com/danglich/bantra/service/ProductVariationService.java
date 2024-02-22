package com.danglich.bantra.service;

import java.util.List;

import com.danglich.bantra.model.ProductVariation;

public interface ProductVariationService {
	
	ProductVariation create(int productId, ProductVariation request);
	
	ProductVariation update(ProductVariation request);
	
	// update 
	ProductVariation updateSoldNumber(int productId, int quantity);
	
	List<ProductVariation> getByProductId(int productId);
		
	void delete(int id);
	
}
