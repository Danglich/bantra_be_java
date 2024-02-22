package com.danglich.bantra.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
	@NotBlank(message = "Email is require")
	@Email
	private String email;
	
	@NotBlank(message = "Password is require")
	private String password;
	
	@NotBlank(message = "Confirm password is require")
	private String confirmPassword;

}
