package com.danglich.bantra.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
	
	private String username;
	
	private String content;
	
	private int rate;
	
	private LocalDateTime createdAt;

}
