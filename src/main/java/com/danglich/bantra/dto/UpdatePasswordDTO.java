package com.danglich.bantra.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordDTO {
	
	@NotBlank(message = "This field is require")
	private String oldPassword;
	
	@NotBlank(message = "This field is require")
	private String newPassword;
	
	@NotBlank(message = "This field is require")
	private String confirmNewPassword;

}
