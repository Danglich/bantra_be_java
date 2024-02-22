package com.danglich.bantra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDTO {
	
	@NotNull(message = "Product id is require")
	@JsonProperty("product_id")
	private Integer productId;
	
	@NotNull(message = "Quantity is require")
	@JsonProperty("quantity")
	private Integer quantity;

}
