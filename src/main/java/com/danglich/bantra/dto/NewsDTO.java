package com.danglich.bantra.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsDTO {
	
	@NotBlank(message = "Title is require")
	private String title;
	
	@NotBlank(message = "Thumbnail is require")
	private String thumbnail;
	
	private LocalDateTime createdAt;
	
	private String author;
	
	private int views;
	
	private int id;
	
	private boolean published;

	@NotBlank(message = "Content is require")
	private String content;
}
