package com.danglich.bantra.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.Review;
import com.danglich.bantra.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final AdminService adminService;
	
	@GetMapping("/reviews")
	public ResponseEntity<List<Review>> getAllReview() {
		
		return ResponseEntity.ok(adminService.getAllReview());
	}
	
	@DeleteMapping("/reviews/{id}")
	public ResponseEntity<SuccessResponse> deleteReview(@PathVariable("id") int theId) {
		adminService.deleteRivew(theId);
		
		return ResponseEntity.ok(SuccessResponse.builder()
					.message("Delete succesfully !")
					.status(HttpStatus.OK.value())
					.build());
					
	}

}
