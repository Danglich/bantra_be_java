package com.danglich.bantra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
	
	private int id;
	
	
	private String name;
	
	private String sku;
	
	private String thumbnail;
	
	private float lowestPrice;
	
	private float highestPrice;
	
	private float rate;
	
	private int reviewNumber;
	
	private int soldNumber;
	
	private int quantity;
	
//	public void setReviews(List<Review> reviews) {
//		List<ReviewDto> reviewDtos = reviews.stream().map(r -> ReviewDto.builder()
//										.content(r.getContent())
//										.username(r.getUser().getFullName())
//										.createdAt(r.getCreatedAt())
//										.rate(r.getRate())
//										.build()
//				).toList();
//		
//		this.reviews = reviewDtos;
//	}

}
