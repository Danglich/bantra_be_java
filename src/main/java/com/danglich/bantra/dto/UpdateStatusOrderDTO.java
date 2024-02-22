package com.danglich.bantra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateStatusOrderDTO {
	
	@JsonProperty(value = "order_id")
	@Min(value = 1, message = "The order id require at least one")
	private int orderId;
	
	@NotBlank(message = "Status is require")
	private String status;

}
