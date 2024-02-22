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

import com.danglich.bantra.dto.CartItemDTO;
import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.CartItem;
import com.danglich.bantra.service.CartItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartItemController {

	// End points
	// POST /cart_items : create
	// PUT /cart_items : update
	// DELETE /cart_items/{product_id} : delete
	// GET /cart_items/{user_id}  : find all by user id

	private final CartItemService service;

	@PostMapping("/cart_items")
	public ResponseEntity<CartItem> create(@Valid @RequestBody CartItemDTO request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));

	}

	@PutMapping("/cart_items")
	public ResponseEntity<CartItem> update(@Valid @RequestBody CartItemDTO request) {

		return ResponseEntity.ok(service.create(request));

	}

	@DeleteMapping("/cart_items/{product_id}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable("product_id") int productId) {
		
		service.delete(productId);
		
		return ResponseEntity.ok(SuccessResponse
											.builder()
											.message("Deleted succesfully")
											.status(HttpStatus.OK.value()).build());
		
	}
	
	@GetMapping("/cart_items/{user_id}")
	public ResponseEntity<List<CartItem>> getByUserId(@PathVariable("user_id") int userId) {
		
		return ResponseEntity.ok(service.findByUserId(userId));
	}

}
