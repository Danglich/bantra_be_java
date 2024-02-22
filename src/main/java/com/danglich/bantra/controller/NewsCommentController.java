package com.danglich.bantra.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danglich.bantra.dto.NewsCommentDTO;
import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.NewsComment;
import com.danglich.bantra.service.NewsCommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NewsCommentController {
	
	// POST   /news_comments   create
	// DELETE /news_comments/{id}   delete
	// GET    /news_comments/{news_id}/top_level    get comments top level
	// GET    /news_comments/{parent_id}/child   get children comment by parent id
	
	private final NewsCommentService service;
	
	@PostMapping("/news_comments")
	public ResponseEntity<NewsComment>  create(@Valid @RequestBody NewsCommentDTO request)  {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
	}
	
	@DeleteMapping("/news_comments/{id}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable(name = "id") int id)  {
		service.delete(id);
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.message("Đã xóa thành công")
				.status(200)
				.build());
	}
	
	@GetMapping("/news_comments/{news_id}/top_level") 
	public ResponseEntity<List<NewsComment>> getTopComment(@PathVariable(name = "news_id") int newsId) {
		
		return ResponseEntity.ok(service.getTopLevelComments(newsId));
	}
	
	@GetMapping("/news_comments/{parent_id}/child") 
	public ResponseEntity<List<NewsComment>> getChildrensComment(@PathVariable(name = "parent_id") int parentId) {
		
		return ResponseEntity.ok(service.getChildComments(parentId));
	}

}
