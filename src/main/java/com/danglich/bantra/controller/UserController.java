package com.danglich.bantra.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.ProductDto;
import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.dto.UpdatePasswordDTO;
import com.danglich.bantra.dto.UpdateUserDetailDTO;
import com.danglich.bantra.model.OrderStatus;
import com.danglich.bantra.model.Role;
import com.danglich.bantra.model.User;
import com.danglich.bantra.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
	// End points 
	// GET  /users/me  get current user
	// GET  /users/{user_id}   Get user by id
	// GET  /users   get all
	// GET  /users/products/liked    Get all liked product
	// PUT /users/update/detail  update user detail
	// PUT /users/update/password   update password
	// PUT /admin/users/disable/{user_id}    disable
	// PUT /users/products/like/{product_id}     like product
	// DELETE   /admin/users/{user_id}    delete
	
	private final UserService userService;
	
	@GetMapping("/users/{user_id}")
	public ResponseEntity<User> getById(@PathVariable("user_id") int userId) {
		
		return ResponseEntity.ok(userService.getById(userId));
	}
	
	@GetMapping("/users/me")
	public ResponseEntity<User> getCurrentUser() {
		
		return ResponseEntity.ok(userService.getCurrentUser());
	}
	
	@GetMapping("/users")
	public ResponseEntity<PaginationResponse> getAll(
			@RequestParam(name = "active", required = false) Boolean active,
			@RequestParam(name = "sort", required = false) String sort,
			@RequestParam(name = "role", required = false) String role,
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "page", defaultValue = "0") int page) throws IllegalAccessException {
		
		
		return ResponseEntity.ok(userService.getByFilters(active, sort, role , keyword, page));
	}
	
	@GetMapping("/users/products/liked")
	public ResponseEntity<List<ProductDto>> getLikedProduct() {
		
		return ResponseEntity.ok(userService.getLikedProducts());
	}
	
	@PutMapping("/users/update/detail")
	public ResponseEntity<User> updateDetail(@Valid @RequestBody UpdateUserDetailDTO request) {
		
		return ResponseEntity.ok(userService.updateDetail(request));
	} 
	
	
	@PutMapping("/users/update/password")
	public ResponseEntity<User> updateDetail(@Valid @RequestBody UpdatePasswordDTO request) {
		
		return ResponseEntity.ok(userService.updatePassword(request));
	} 
	
	@PutMapping("/users/products/like/{product_id}")
	public ResponseEntity<User> likeProduct(@PathVariable("product_id") int productId) {
		
		
		return ResponseEntity.ok(userService.likeProduct(productId));
	} 
	
	@PutMapping("/users/disable/{user_id}")
	public ResponseEntity<User> disable(@PathVariable("user_id") int userId) {
		
		return ResponseEntity.ok(userService.disable(userId));
	} 
	
	@DeleteMapping("/users/{user_id}")
	public ResponseEntity<SuccessResponse> deleteByCategory(@PathVariable(name = "user_id") int userId) {
		
		userService.delete(userId);
		
		SuccessResponse response = SuccessResponse.builder()
											.message("Delete user succesfully!")
											.status(200)
											.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	

}
