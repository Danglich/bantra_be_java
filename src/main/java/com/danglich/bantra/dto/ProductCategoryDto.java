package com.danglich.bantra.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategoryDto {
	
	private String name;
	private String thumbnail;

}
