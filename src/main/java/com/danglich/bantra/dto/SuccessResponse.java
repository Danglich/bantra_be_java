package com.danglich.bantra.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse {
	
	private String message;
	private int status;
	
	@Builder.Default
	private LocalDateTime timestemp = LocalDateTime.now();
	
	

}
