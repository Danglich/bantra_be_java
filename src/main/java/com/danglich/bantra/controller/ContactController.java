package com.danglich.bantra.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.model.Contact;
import com.danglich.bantra.service.ContactService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContactController {
	
	// End points
	//  POST  /contacts  create
	//  GET   /contacts  get all
	
	private final ContactService service;
	
	@PostMapping("/contacts")
	public ResponseEntity<Contact> create(@Valid @RequestBody Contact request) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
	}
	
	@GetMapping("/contacts")
	public ResponseEntity<PaginationResponse> getAll(@RequestParam(name = "startDate", required = false) String startDate,
			@RequestParam(name = "endDate", required = false) String endDate,
			@RequestParam(name = "page", defaultValue = "0") int page) {
		
			String pattern = "yyyy-MM-dd HH:mm:ss";
	        
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			
	        LocalDateTime sd = null;
	        LocalDateTime ed = null;
	        
	        if(startDate != null) {
	        	sd = LocalDateTime.parse(startDate + " 00:00:00", formatter);
	        }
	        if(endDate != null) {
	        	ed = LocalDateTime.parse(endDate +  " 00:00:00", formatter);
	        	
	        }
	
			return ResponseEntity.ok(service.getByFilters(sd, ed, page));
	}

}
