package com.danglich.bantra.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danglich.bantra.dto.ProductCategoryDto;
import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.ProductCategory;
import com.danglich.bantra.service.ProductCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
//@CrossOrigin
//(origins = "*", methods = {RequestMethod.POST , RequestMethod.GET })
public class ProductCategoryController {

	private final ProductCategoryService service;

	@PostMapping("/admin/product_categories")
	public ResponseEntity<SuccessResponse> save(@RequestBody ProductCategoryDto request) {
		service.save(request);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(
				SuccessResponse.builder().message("Create successfully!").status(202).build());
	}
	
	@GetMapping("/product_categories/{slug}")
	public ResponseEntity<ProductCategory> getBySlug(@PathVariable(name = "slug") String slug) {
		
		return ResponseEntity.ok(service.findBySlug(slug));
	}

	@GetMapping("/product_categories")
	public ResponseEntity<List<ProductCategory>> getAll() {

		List<ProductCategory> categories = service.getAll();
		return ResponseEntity.ok(categories);
	}
}
