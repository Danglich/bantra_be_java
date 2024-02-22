package com.danglich.bantra.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.danglich.bantra.dto.AuthenticationRequest;
import com.danglich.bantra.dto.AuthenticationResponse;
import com.danglich.bantra.dto.RegisterRequest;
import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.CustomUserDetails;
import com.danglich.bantra.model.Role;
import com.danglich.bantra.model.User;
import com.danglich.bantra.repository.UserRepository;
import com.danglich.bantra.service.AuthService;
import com.danglich.bantra.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements UserDetailsService , AuthService  {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@Override
	public UserDetails loadUserByUsername(String username) {
		
		User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		return new CustomUserDetails(user );
	}
	
	public AuthenticationResponse register(RegisterRequest request) throws Exception {
		
		if(!request.getPassword().equals(request.getConfirmPassword())) {
			throw new Exception("Invalid password !");
		}
		
		boolean isExisted = userRepository.findByEmail(request.getEmail()).isPresent();
		
		if(isExisted) {
			throw new Exception("User already existed");
		}
		
		User user =  User.builder()
				.email(request.getEmail())
				.role(Role.USER)
				.password(passwordEncoder.encode(request.getPassword()))
				.registered(true)
				.active(true)
				.build();
		
		userRepository.save(user);

		String token = jwtService.generateToken(new CustomUserDetails(user));
		
		return AuthenticationResponse.builder().token(token).build();
	}
	
	public AuthenticationResponse login(AuthenticationRequest request) throws Exception {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			
		} catch (Exception e) {
			throw new Exception("Incorrect usernam or password !");
		}
		
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		String token = jwtService.generateToken(new CustomUserDetails(user));
		
		return AuthenticationResponse.builder().token(token).build();
		
		
	}

	@Override
	public User getByToken(String token) {
		String userEmail = jwtService.extractUsername(token);
		
		User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		
		return user;
	}

	@Override
	public AuthenticationResponse loginAdmin(AuthenticationRequest request) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			
		} catch (Exception e) {
			throw new Exception("Incorrect usernam or password !");
		}
		
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		if(user.getRole() != Role.ADMIN) {
			throw new IllegalAccessException("Bạn không phải làm quản trị viên");
		}
		
		String token = jwtService.generateToken(new CustomUserDetails(user));
		
		return AuthenticationResponse.builder().token(token).build();
	}

	

}
