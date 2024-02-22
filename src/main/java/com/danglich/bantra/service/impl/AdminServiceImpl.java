package com.danglich.bantra.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.danglich.bantra.model.Review;
import com.danglich.bantra.service.AdminService;
import com.danglich.bantra.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
	
	private final ReviewService reviewService;

	@Override
	public List<Review> getAllReview() {
		
		return reviewService.getAllOrderByCreatedAt();
	}

	@Override
	public void deleteRivew(int theId) {
		reviewService.delete(theId);
		
	}

}
