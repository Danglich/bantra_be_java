package com.danglich.bantra.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.NewsCategory;
import com.danglich.bantra.service.NewsCategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NewsCategoryController {

	private final NewsCategoryService service;

	@GetMapping("/news_categories")
	public ResponseEntity<List<NewsCategory>> getAll() {

		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/news_categories/{slug}")
	public ResponseEntity<NewsCategory> getBySlug(@PathVariable(name = "slug") String slug) {

		return ResponseEntity.ok(service.getBySlug(slug));
	}

	@PostMapping("/admin/news_categories")
	public ResponseEntity<NewsCategory> create(@Valid @RequestBody NewsCategory request) {

		return ResponseEntity.ok(service.create(request));
	}

	@PutMapping("/admin/news_categories")
	public ResponseEntity<NewsCategory> update(@Valid @RequestBody NewsCategory request) {

		return ResponseEntity.ok(service.update(request));
	}

	@DeleteMapping("/admin/news_categories/{id}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable int theId) {

		service.delete(theId);

		return ResponseEntity.ok(SuccessResponse.builder().message("Delete succesfully !").status(HttpStatus.OK.value())
				.build());
	}

}
