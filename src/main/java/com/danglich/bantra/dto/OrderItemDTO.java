package com.danglich.bantra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class OrderItemDTO {
	
	@JsonProperty(value = "product_id")
	@Min(value = 1, message = "Product id require at least one")
	private int productId;
	
	@Min(value = 1, message = "Quantity require at least one")
	private int quantity;

}
