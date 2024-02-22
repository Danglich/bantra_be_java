package com.danglich.bantra.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserDetailDTO {
	
	@Email
	@NotBlank(message = "Email is require")
	private String email;
	
	private Boolean gender;
	
	@JsonProperty(value = "full_name")
	private String fullName;
	
	@JsonProperty(value = "phone_number")
	private String phoneNumber;

}
