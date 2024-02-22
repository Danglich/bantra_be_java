package com.danglich.bantra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.ProductProperty;
import com.danglich.bantra.service.ProductPropertyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductPropertyController {
	
	// end points
	// POST /products/{product_id}/product_properties : create property
	// PUT /product_properties  : update property
	// DELETE /product_properties/{id}
	
	private final ProductPropertyService service;
	
	@PostMapping("/products/{product_id}/product_properties")
	public ResponseEntity<ProductProperty> create(@PathVariable("product_id") int productId , @RequestBody ProductProperty request) {
		
		return ResponseEntity.ok(service.create(productId, request));
	}
	
	@PutMapping("/product_properties")
	public ResponseEntity<ProductProperty> update(@RequestBody ProductProperty request) {
		
		return ResponseEntity.ok(service.update(request));
	}
	
	
	@DeleteMapping("/product_properties/{id}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable("id") int theId) {
		
		service.delete(theId);
		
		return ResponseEntity.ok(SuccessResponse.builder()
					.message("Delete succesfully !")
					.status(HttpStatus.OK.value())
					.build());
					
	}

}
