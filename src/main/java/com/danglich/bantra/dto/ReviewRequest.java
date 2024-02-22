package com.danglich.bantra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRequest {
	
	@NotNull(message = "Order id is required")
	private Integer orderId;
	
	@NotNull(message = "Content id is required")
	@NotBlank(message = "Content is not empty")
	private String content;
	
	@NotNull(message = "Rate id is required")
	private Integer rate;

}
