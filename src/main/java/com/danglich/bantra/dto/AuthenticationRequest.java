package com.danglich.bantra.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
	
	@Email
	@NotBlank(message = "Email is require")
	private String email;
	
	@NotBlank(message = "Password is require")
	private String password;

}
