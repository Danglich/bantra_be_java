package com.danglich.bantra.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class OrderRequestDTO {

	@JsonProperty(value = "address_id")
	@NotNull(message = "Address id is require")
	private int addressId;
	
	@JsonProperty(value = "user_id")
	private int userId;
	
	@NotNull(message = "Payment id is require")
	@JsonProperty(value = "payment_id")
	private int paymentId;
	
	@NotNull(message = "Shipping method id is require")
	@JsonProperty(value = "shipping_id")
	private int shippingId;
	
	@NotEmpty(message = "Order require at least one item")
	private List<OrderItemDTO> items;
	
	
	private String note;
}
