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
import com.danglich.bantra.model.ProductInfo;
import com.danglich.bantra.service.ProductInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ProductInfoController {
	
	// end points
	// POST /products/{product_id}/product_infos : create information
	// PUT /product_infos  : update information
	// DELETE /product_infos/{id}
	
	private final ProductInfoService service;
	
	@PostMapping("/products/{product_id}/product_infos")
	public ResponseEntity<ProductInfo> create(@PathVariable("product_id") int productId , @RequestBody ProductInfo request) {
		
		return ResponseEntity.ok(service.create(productId, request));
	}
	
	@PutMapping("/product_infos")
	public ResponseEntity<ProductInfo> update(@RequestBody ProductInfo request) {
		
		return ResponseEntity.ok(service.update(request));
	}
	
	
	@DeleteMapping("/product_infos/{id}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable("id") int theId) {
		
		service.delete(theId);
		
		return ResponseEntity.ok(SuccessResponse.builder()
					.message("Delete succesfully !")
					.status(HttpStatus.OK.value())
					.build());
					
	}

}
