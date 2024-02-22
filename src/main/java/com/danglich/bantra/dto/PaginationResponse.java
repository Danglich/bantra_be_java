package com.danglich.bantra.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationResponse {
	
	private long totalItems;
	
	private int totalPages;
	
	private int currentPage;
	
	private Object data;

}
