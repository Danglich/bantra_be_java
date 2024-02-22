package com.danglich.bantra.service;


import java.time.LocalDateTime;
import java.util.List;

import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.ReviewDto;
import com.danglich.bantra.dto.ReviewRequest;
import com.danglich.bantra.model.Review;

public interface ReviewService {
	
	Review create(ReviewRequest request);
	
	ReviewDto update(Review request);
	
	void delete(int theId);
	
	List<Review> getAllOrderByCreatedAt();
	
	List<Review> getTop5();
	
	PaginationResponse getByFilters(LocalDateTime startDate , LocalDateTime endDate, Integer rate , int pageNumber );

}
