package com.danglich.bantra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsCommentDTO {
	
	@NotBlank(message = "Yêu cầu phải có nội dung")
	@NotNull(message = "Yêu cầu phải có nội dung")
	private String content;
	
	@NotNull(message = "Yêu cầu phải có news id")
	private Integer newsId;
	
	private Integer parentId;

}
