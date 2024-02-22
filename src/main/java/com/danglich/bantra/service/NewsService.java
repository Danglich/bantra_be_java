package com.danglich.bantra.service;

import java.time.LocalDateTime;
import java.util.List;

import com.danglich.bantra.dto.NewsDTO;
import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.model.News;

public interface NewsService {

	News getById(int newsId);
	
	PaginationResponse getAllByKeyword(String keyword , int pageNumber);
	
	PaginationResponse getByFilters(LocalDateTime startDate , LocalDateTime endDate, Boolean published , String keyword ,String sortBy , int pageNumber );
	
	List<News> getByCategory(int categoryId);
	
	PaginationResponse getByUser(int userId, int pageNumber);
	
	News create(NewsDTO request, int categoryId);
	
	News update(News request);
	
	void delete(int newsId);
	
	News increaseViews(int newsId);
	
	List<News> getTop5ByView();
	
	List<News> getAll();
}
