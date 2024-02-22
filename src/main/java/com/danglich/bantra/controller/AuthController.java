package com.danglich.bantra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danglich.bantra.dto.AuthenticationRequest;
import com.danglich.bantra.dto.AuthenticationResponse;
import com.danglich.bantra.dto.RegisterRequest;
import com.danglich.bantra.model.User;
import com.danglich.bantra.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@GetMapping("") 
	public ResponseEntity<User> getByToken(@RequestParam(name = "token" , required = true) String token) {
		
		return ResponseEntity.ok(authService.getByToken(token));
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) throws Exception {
		
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) throws Exception {
		
		return ResponseEntity.ok(authService.login(request));
	}
	
	@PostMapping("/admin/login")
	public ResponseEntity<AuthenticationResponse> authenticateAdmin(@Valid @RequestBody AuthenticationRequest request) throws Exception {
		
		return ResponseEntity.ok(authService.loginAdmin(request));
	}
}
