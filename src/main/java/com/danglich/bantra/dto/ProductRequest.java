package com.danglich.bantra.dto;

import java.util.List;

import com.danglich.bantra.model.ProductInfo;
import com.danglich.bantra.model.ProductMedia;
import com.danglich.bantra.model.ProductProperty;
import com.danglich.bantra.model.ProductVariation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
	
	private int id;
	
	@NotEmpty(message = "Requires at least one property")
	private List<ProductProperty> properties;
	
	@NotEmpty(message = "Requires at least one information")
	private List<ProductInfo> informations;
	
//	@NotEmpty(message = "Requires at least one product media")
//	private List<ProductMedia> medias;
	
	@NotEmpty(message = "Requires at least one variation")
	private List<ProductVariation> variations;
	
	@NotNull
	@NotBlank(message = "Name is require")
	private String name;
	
	@NotNull
	@NotBlank(message = "Description is require")
	private String description;
	
	@NotNull
	@NotBlank(message = "SKU code is require")
	private String sku;
	
	@NotBlank(message = "Thumbnail is require")
	private String thumbnail;

}
