package com.danglich.bantra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateSoldNumberRequest {
	
	@NotNull(message = "Product is require")
	@JsonProperty(value = "product_id")
	private int productId;
	
	@NotNull(message = "Product is require")
	private int quantity;

}
