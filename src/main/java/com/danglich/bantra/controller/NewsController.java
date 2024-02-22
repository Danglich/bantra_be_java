package com.danglich.bantra.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danglich.bantra.dto.NewsDTO;
import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.News;
import com.danglich.bantra.service.NewsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NewsController {

	// End points
	// GET /news/{news_id} get by id / News
	// GET /news/top   get top 5 by view
	// GET /admin/news get all / List<NewsDTO>
	// GET /news get all / List<News>
	// GET /news_categories/news/{category_id} get by category
	// GET /users/news/{user_id} get by user
	// POST /news/{category_id} create news
	// PUT /news update
	// DELETE /news/{news_id} delete
	// PUT /news/update/views update views

	private final NewsService service;
	
	@GetMapping("/news/top")
	public ResponseEntity<List<News>> getTop5ByView() {

		return ResponseEntity.ok(service.getTop5ByView());
	}
	
	@GetMapping("/news/search")
	public ResponseEntity<PaginationResponse> search(@RequestParam(name = "page", defaultValue = "0") int page, 
			@RequestParam(name = "keyword", required = false) String keyword) {

		return ResponseEntity.ok(service.getAllByKeyword(keyword, page));
	}

	@GetMapping("/news/{news_id}")
	public ResponseEntity<News> getById(@PathVariable("news_id") int theId) {

		return ResponseEntity.ok(service.getById(theId));
	}
	
	@GetMapping("/news")
	public ResponseEntity<List<News>> getAll() {

		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/admin/news")
	public ResponseEntity<PaginationResponse> getAll(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "startDate", required = false) String startDate,
			@RequestParam(name = "endDate", required = false) String endDate,
			@RequestParam(name = "published", required = false) Boolean published,
			@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "page", defaultValue = "0") int page) {
		

        String pattern = "yyyy-MM-dd HH:mm:ss";
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		
        LocalDateTime sd = null;
        LocalDateTime ed = null;
        
        if(startDate != null) {
        	sd = LocalDateTime.parse(startDate + " 00:00:00", formatter);
        }
        if(endDate != null) {
        	ed = LocalDateTime.parse(endDate +  " 00:00:00", formatter);
        	
        }

		return ResponseEntity.ok(service.getByFilters(sd, ed,published , keyword,sortBy , page));
	}

	@GetMapping("/news_categories/news/{category_id}")
	public ResponseEntity<List<News>> getByCategory(
			@PathVariable("category_id") int categoryId) {

		return ResponseEntity.ok(service.getByCategory(categoryId));
	}

	@GetMapping("/users/news/{user_id}")
	public ResponseEntity<PaginationResponse> getByUser(@RequestParam(name = "page", defaultValue = "0") int page,
			@PathVariable("user_id") int userId) {

		return ResponseEntity.ok(service.getByUser(userId, page));
	}

	@PostMapping("/admin/news/{category_id}")
	public ResponseEntity<News> create(@PathVariable("category_id") int categoryId,
			@Valid @RequestBody NewsDTO request) {

		return ResponseEntity.ok(service.create(request, categoryId));
	}

	@PutMapping("/admin/news")
	public ResponseEntity<News> update(@Valid @RequestBody News request) {

		return ResponseEntity.ok(service.update(request));
	}

	@PutMapping("/news/update/views/{news_id}")
	public ResponseEntity<News> updateViews(@PathVariable("news_id") int theId) {

		return ResponseEntity.ok(service.increaseViews(theId));
	}

	@DeleteMapping("/admin/news/{news_id}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable("news_id") int theId) {

		service.delete(theId);

		return ResponseEntity
				.ok(SuccessResponse.builder().message("Delete succesfully !").status(HttpStatus.OK.value()).build());

	}

}
