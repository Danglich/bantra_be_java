package com.danglich.bantra.service;



import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.danglich.bantra.dto.AuthenticationRequest;
import com.danglich.bantra.dto.AuthenticationResponse;
import com.danglich.bantra.dto.RegisterRequest;
import com.danglich.bantra.model.User;

public interface AuthService {

	AuthenticationResponse register(RegisterRequest request) throws Exception;
	
	 AuthenticationResponse login(AuthenticationRequest request) throws Exception;
	 
	 AuthenticationResponse loginAdmin(AuthenticationRequest request) throws Exception;
	 
	 User getByToken(String token);

	 
	 
}
