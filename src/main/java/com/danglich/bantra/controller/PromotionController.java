package com.danglich.bantra.controller;

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
import com.danglich.bantra.dto.PromotionRequest;
import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.Promotion;
import com.danglich.bantra.service.PromotionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PromotionController {
	
	// End points
	// GET   /promotions    get all promotion
	// POST  /admin/promotions    create new
	// PUT   /admin/promotions    update promotion
	// PUT   /admin/promotions/add        add category
	// PUT   /admin/promotions/remove     remove category
	// DELETE  /admin/promotions/{promotion_id}         delete promotion
	
	private final PromotionService promotionService;
	
	@GetMapping("/promotions")
	public ResponseEntity<PaginationResponse>  getAll(
			@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "categoryId", required = false) Integer endDate,
			@RequestParam(name = "page", defaultValue = "0") int page) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(promotionService.getAll(page, endDate, status));
	}
	
	@PostMapping("/admin/promotions")
	public ResponseEntity<Promotion> create(@Valid @RequestBody PromotionRequest request) {
		
		return ResponseEntity.ok(promotionService.create(request));
	}
	
	@PutMapping("/admin/promotions")
	public ResponseEntity<Promotion> update(@Valid @RequestBody PromotionRequest request) {
		
		return ResponseEntity.ok(promotionService.update(request));
	}
	
	@DeleteMapping("/admin/promotions/{id}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable(name = "id") int theId) {
		
		promotionService.delete(theId);
		
		return ResponseEntity.ok(SuccessResponse.builder()
				.message("Delete promotion succesfully")
				.status(200)
				.build());
	}

}
