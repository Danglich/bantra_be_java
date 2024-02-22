package com.danglich.bantra.service;


import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.PromotionRequest;
import com.danglich.bantra.model.Promotion;

public interface PromotionService {
	
	PaginationResponse getAll(int pageNumber, Integer categoryId, String status);
	
	Promotion create(PromotionRequest request);
	
	Promotion update(PromotionRequest request);
	
	void delete(int theId);

}
