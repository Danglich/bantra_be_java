package com.danglich.bantra.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PromotionRequest {
	
	private int id;
	
	private String description;
	
	@NotBlank(message = "Thumbnail is require")
	private String thumbnail;
	
	@Min(message = "Thumbnail is require", value = 1)
	private int discountRate;
	
	@NotNull(message = "This field is require")
	private String startDate;
	
	@NotNull(message = "This field is require")
	private String endDate;
	
	@NotNull(message = "This field is require")
	private List<Integer> categoryIds;

}
