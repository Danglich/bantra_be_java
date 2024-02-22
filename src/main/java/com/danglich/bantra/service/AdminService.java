package com.danglich.bantra.service;

import java.util.List;

import com.danglich.bantra.model.Review;

public interface AdminService {
	
	// Review
	List<Review> getAllReview(); 
	void deleteRivew(int theId);

}
