package com.danglich.bantra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateOrderDTO {
	
	@JsonProperty(value = "order_id")
	@Min(value = 1 , message = "The order id require at least one")
	private int orderId;
	
	@Min(value = 1 , message = "The address id require at least one")
	@JsonProperty(value = "address_id")
	private int addressId;
	
	@Min(value = 1 , message = "The payment id require at least one")
	@JsonProperty(value = "payment_id")
	private int paymentId;
	
	@Min(value = 1 , message = "The shipping id require at least one")
	@JsonProperty(value = "shipping_id")
	private int shippingId;
	
	private String note;

}
