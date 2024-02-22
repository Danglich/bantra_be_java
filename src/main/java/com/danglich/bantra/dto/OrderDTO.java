package com.danglich.bantra.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {
	
	private int id;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime sentAt;
	
	private int total;
	
	private String paymentMethod;
	
	private String shippingMethod;
	
	private String status;
	
	private String address;

}
