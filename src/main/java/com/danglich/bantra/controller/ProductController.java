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
import com.danglich.bantra.dto.ProductDto;
import com.danglich.bantra.dto.ProductRequest;
import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.Product;
import com.danglich.bantra.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	// End points
	// GET /products : get all products
	// GET /product_categories/{category_id}/products : get all product by category id
	// GET /products/{id} : get product by id
	// GET /products/best_selling    Get top product
	// POST /admin/product_categories/{category_id}/products : create new product
	// PUT /admin/products : update product by id
	// PUT /products/update/sold_number
	// DELETE /admin/products/{id} : delete product by id
	// DELETE /admin/product_categories/{category_id}/products : delete all products by category id
	
	@PostMapping("/product_categories/{category_id}/products")
	public ResponseEntity<Product> createProduct(@PathVariable(name = "category_id") int categoryId, @Valid @RequestBody ProductRequest productRequest ) {
		Product result = productService.createProduct(categoryId , productRequest);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
		
	}
	
	
	@GetMapping("/products")
	public ResponseEntity<PaginationResponse> getAll(@RequestParam(name = "page", defaultValue = "0") int pageNumber,
													@RequestParam(name = "keyword", required = false) String keyword,
													@RequestParam(name = "categoryId", required = false) Integer categoryId,
													@RequestParam(name = "sku", required = false) String sku,
													@RequestParam(name = "sortBy", required = false) String sortString) {
		
		
		return ResponseEntity.ok(productService.getAll(keyword , sku ,categoryId ,pageNumber, sortString));
	}
	
	@GetMapping("/product_categories/{category_id}/products")
	public ResponseEntity<PaginationResponse> getProductsByCategory(@RequestParam(name = "page", defaultValue = "0") int page ,
			@PathVariable(name = "category_id") int categoryId , 
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "price", required = false) String price,
			@RequestParam(name = "sort", required = false) String sortString) {
		
			Float maxPrice = null;
			Float minPrice = null;
			if(price != null ) {
				
				try {
					minPrice = Float.parseFloat(price.split("-")[0]);
					
					
					if(price.split("-")[1] != null) 
						maxPrice = Float.parseFloat(price.split("-")[1]);
					
				} catch (Exception e) {
					
					throw new IllegalArgumentException("Filter is invalid");
				}
			}
		
		return ResponseEntity.ok(productService.getByCategory(categoryId, keyword, page, sortString, minPrice, maxPrice));
		
	}
	
	@GetMapping("/products/{product_id}")
	public ResponseEntity<Product> getById(@PathVariable(name = "product_id") int productId) {
		
		Product product = productService.getById(productId);
		return ResponseEntity.ok(product);
	}
	
	@GetMapping("/products/best_selling")
	public ResponseEntity<List<ProductDto>> getTopBestSelling() {
		
		return ResponseEntity.ok( productService.getTopProduct());
	}
	
	@PutMapping("/products")
	public ResponseEntity<Product> update(@Valid @RequestBody Product productRequest) {
		
		return ResponseEntity.ok(productService.update(productRequest));
	}
	
//	@PutMapping("/products/update/sold_number")
//	public ResponseEntity<Product> updateSoldNumber(@Valid @RequestBody UpdateSoldNumberRequest request) {
//		
//		return ResponseEntity.ok(productService.updateSoldNumber(request));
//	}
	
	@DeleteMapping("/products/{product_id}")
	public ResponseEntity<SuccessResponse> deleteById(@PathVariable(name = "product_id") int productId) {
		
		productService.deleteById(productId);
		
		SuccessResponse response = SuccessResponse.builder()
											.message("Delete product succesfully!")
											.status(200)
											.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/product_categories/{category_id}/products")
	public ResponseEntity<SuccessResponse> deleteByCategory(@PathVariable(name = "category_id") int categoryId) {
		
		long productsDeleted = productService.deleteByCategory(categoryId);
		
		SuccessResponse response = SuccessResponse.builder()
											.message("Delete "+ productsDeleted  + " product succesfully!")
											.status(200)
											.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
