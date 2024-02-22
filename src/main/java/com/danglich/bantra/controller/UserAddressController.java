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

import com.danglich.bantra.dto.AddressRequest;
import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.UserAddress;
import com.danglich.bantra.service.UserAddressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserAddressController {
	
	// end points
	// POST /users/addresses : create
	// GET  /users/{user_id}/addresses : get all addresses by user id
	// PUT  /addresses/set_default/{id} : set default
	
	private final UserAddressService service;
	
	@PostMapping("/users/addresses")
	public ResponseEntity<UserAddress> create( @Valid @RequestBody AddressRequest request) {
		
		return ResponseEntity.ok(service.create(request));
	}
	
	@PutMapping("/addresses/set_default/{id}")
	public ResponseEntity<UserAddress> setDefault(@PathVariable("id") int theId) {
		
		return ResponseEntity.ok(service.setDefault(theId));
	}
	
	@GetMapping("/users/{user_id}/addresses")
	public ResponseEntity<List<UserAddress>> getByUserId(@PathVariable("user_id") int userId) {
		
		return ResponseEntity.ok(service.getByUserId(userId));
	}
	
	
	

}
