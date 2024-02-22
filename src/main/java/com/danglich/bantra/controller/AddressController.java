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

import com.danglich.bantra.dto.AddressRequest;
import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.Address;
import com.danglich.bantra.service.AddressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {
	// POST  /addresses  create 
	// PUT   /addresses  update
	// DELETE  /addresses  delete 
	
	private final AddressService service;
	
	@PostMapping("/addresses")
	public ResponseEntity<Address> create(@Valid @RequestBody AddressRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
	}
	
	@PutMapping("/addresses")
	public ResponseEntity<Address> update(@Valid @RequestBody Address request) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.update(request));
	}
	
	@DeleteMapping("/addresses/{id}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable(name = "id") int id) {
		
		service.delete(id);
		return ResponseEntity.ok(SuccessResponse.builder()
				.message("The address deleted successfully")
				.status(200)
				.build()
				);
	}

}
