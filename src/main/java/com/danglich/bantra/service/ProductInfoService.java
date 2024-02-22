package com.danglich.bantra.service;

import com.danglich.bantra.model.ProductInfo;

public interface ProductInfoService {
	
	ProductInfo create(int productId, ProductInfo request);
	
	ProductInfo update(ProductInfo request);
	
	void delete(int id);
	
}
