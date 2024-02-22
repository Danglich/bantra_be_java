package com.danglich.bantra.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.ReviewDto;
import com.danglich.bantra.dto.ReviewRequest;
import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.Review;
import com.danglich.bantra.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
	
	// end points
		// GET /reviews   get all reviews
		// GET /reviews/top   get top 5 lastest reviews
		// POST /products/{product_id}/reviews : create review
		// PUT /reviews  : update review
		// DELETE /reviews/{id} : delete review
	
	private final ReviewService service;
	
	@GetMapping("/reviews")
	public ResponseEntity<PaginationResponse> getAll(
			@RequestParam(name = "startDate", required = false) String startDate,
			@RequestParam(name = "endDate", required = false) String endDate,
			@RequestParam(name = "rate", required = false) Integer rate,
			@RequestParam(name = "page", defaultValue = "0") int page) {
		
		//DateTimeFormatter df = DateTimeFormatter .ofPattern("YYYY-MM-DD");
		

        String pattern = "yyyy-MM-dd HH:mm:ss";
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		
        LocalDateTime sd = null;
        LocalDateTime ed = null;
        
        if(startDate != null) {
        	sd = LocalDateTime.parse(startDate + " 00:00:00", formatter);
        }
        if(endDate != null) {
        	ed = LocalDateTime.parse(endDate +  " 00:00:00", formatter);
        	
        }

		return ResponseEntity.ok(service.getByFilters(sd, ed,rate , page));
	}
	
	@PostMapping("/reviews")
	public ResponseEntity<Review> create(@RequestBody @Valid ReviewRequest request) {
		
		return ResponseEntity.ok(service.create(request));
	}
	
	@GetMapping("/reviews/top")
	public ResponseEntity<List<Review>> getTop5() {
		
		return ResponseEntity.ok(service.getTop5());
	}
	
	@PutMapping("/reviews")
	public ResponseEntity<ReviewDto> update(@RequestBody @Valid Review request) {
		
		return ResponseEntity.ok(service.update(request));
	}
	
	@DeleteMapping("/reviews/{id}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable("id") int theId) {
		
		service.delete(theId);
		
		return ResponseEntity.ok(SuccessResponse.builder()
					.message("Delete succesfully !")
					.status(HttpStatus.OK.value())
					.build());
					
	}
	
	
	

}
