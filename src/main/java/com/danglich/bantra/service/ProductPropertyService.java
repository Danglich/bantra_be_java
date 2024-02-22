package com.danglich.bantra.service;

import com.danglich.bantra.model.ProductProperty;

public interface ProductPropertyService {
	
	ProductProperty create(int productId, ProductProperty request);
	
	ProductProperty update(ProductProperty request);
	
	void delete(int id);

	
}
