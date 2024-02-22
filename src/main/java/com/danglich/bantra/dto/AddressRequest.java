package com.danglich.bantra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressRequest {
	
	@NotNull(message = "This field is require")
	@NotBlank(message = "This field is require")
	private String fullName;
	
	@NotNull(message = "This field is require")
	@NotBlank(message = "This field is require")
	private String phoneNumber;
	
	@NotNull(message = "This field is require")
	@NotBlank(message = "This field is require")
	private String email;

	@NotNull(message = "This field is require")
	@NotBlank(message = "This field is require")
	private String province;
	
	@NotNull(message = "This field is require")
	@NotBlank(message = "This field is require")
	private String district;
	
	@NotNull(message = "This field is require")
	@NotBlank(message = "This field is require")
	private String ward;
	
	@NotNull(message = "This field is require")
	@NotBlank(message = "This field is require")
	private String detail;
}
