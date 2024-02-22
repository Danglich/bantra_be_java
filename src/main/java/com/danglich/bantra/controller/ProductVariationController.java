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
import org.springframework.web.bind.annotation.RestController;

import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.ProductVariation;
import com.danglich.bantra.service.ProductVariationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductVariationController {
	
	// end points
	// POST /admin/products/{product_id}/product_variations : create property
	// PUT /admin/product_variations  : update property
	// DELETE /admin/product_variations/{id}
	// GET   /products/{productId}/product_variations   get by productId
	
	private final ProductVariationService service;
	
	@PostMapping("/admin/products/{product_id}/product_variations")
	public ResponseEntity<ProductVariation> create(@PathVariable("product_id") int productId ,@Valid @RequestBody ProductVariation request) {
		
		return ResponseEntity.ok(service.create(productId, request));
	}
	
	@GetMapping("/products/{productId}/product_variations") 
	public ResponseEntity<List<ProductVariation>> getByProductId(@PathVariable(name = "productId") int productId) {
		
		
		return ResponseEntity.ok(service.getByProductId(productId));
	}
	
	@PutMapping("/admin/product_variations")
	public ResponseEntity<ProductVariation> update(@Valid @RequestBody ProductVariation request) {
		
		return ResponseEntity.ok(service.update(request));
	}
	
	
	@DeleteMapping("/admin/product_variations/{id}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable("id") int theId) {
		
		service.delete(theId);
		
		return ResponseEntity.ok(SuccessResponse.builder()
					.message("Delete succesfully !")
					.status(HttpStatus.OK.value())
					.build());
					
	}

}
