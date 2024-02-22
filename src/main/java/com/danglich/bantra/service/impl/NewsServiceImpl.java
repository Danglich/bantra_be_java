package com.danglich.bantra.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.danglich.bantra.dto.NewsDTO;
import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.News;
import com.danglich.bantra.model.NewsCategory;
import com.danglich.bantra.model.User;
import com.danglich.bantra.repository.NewsCategoryRepository;
import com.danglich.bantra.repository.NewsRepository;
import com.danglich.bantra.service.NewsService;
import com.danglich.bantra.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

	private final NewsRepository repository;
	private final NewsCategoryRepository categoryRepository;
	private final UserService userService;

	@Value("${pagination.size}")
	private int pageSize;

	private NewsDTO mapToNewsDto(News request) {

		return NewsDTO.builder().content(request.getContent()).createdAt(request.getCreatedAt())
				.thumbnail(request.getThumbnail()).title(request.getTitle()).views(request.getViews())
				.author(request.getUser().getEmail()).id(request.getId()).published(request.isPublished()).build();
	}

	@Override
	public News getById(int newsId) {
		News news = repository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("Not found the news"));

		return news;
	}

	@Override
	public PaginationResponse getAllByKeyword(String keyword, int pageNumber) {

		Pageable paging = PageRequest.of(pageNumber, pageSize);

		Page<News> pageTuts;

		if (keyword == null || keyword.trim().equals("")) {
			pageTuts = repository.findAll(paging);
		} else {
			pageTuts = repository.searchByTitleOrContent(keyword.trim(), keyword.trim(), paging);
		}
		//List<NewsDTO> newsDto = pageTuts.getContent().stream().map(news -> mapToNewsDto(news)).toList();

		return PaginationResponse.builder().currentPage(pageTuts.getNumber()).totalItems(pageTuts.getTotalElements())
				.data(pageTuts.getContent()).totalPages(pageTuts.getTotalPages()).build();
	}

	@Override
	public List<News> getByCategory(int categoryId) {
		//Pageable paging = PageRequest.of(pageNumber, pageSize);

		List<News> news = repository.findByCategoryIdOrderByCreatedAtDesc(categoryId);

		//List<NewsDTO> newsDto = pageTuts.getContent().stream().map(news -> mapToNewsDto(news)).toList();

		return news;
	}

	@Override
	public PaginationResponse getByUser(int userId, int pageNumber) {

		Pageable paging = PageRequest.of(pageNumber, pageSize);

		Page<News> pageTuts = repository.findByUserId(userId, paging);

		List<NewsDTO> newsDto = pageTuts.getContent().stream().map(news -> mapToNewsDto(news)).toList();

		return PaginationResponse.builder().currentPage(pageTuts.getNumber()).totalItems(pageTuts.getTotalElements())
				.data(newsDto).totalPages(pageTuts.getTotalPages()).build();
	}

	@Override
	public News create(NewsDTO request, int categoryId) {

		User user = userService.getCurrentUser();

		NewsCategory category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found news category"));

		return repository.save(News.builder().user(user).category(category).content(request.getContent())
				.thumbnail(request.getThumbnail()).title(request.getTitle()).createdAt(LocalDateTime.now())
				.published(false).build()

		);
	}

	@Override
	public News update(News request) {

		News news = repository.findById(request.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Not found the news"));

		news.setContent(request.getContent());
		news.setThumbnail(request.getThumbnail());
		news.setTitle(request.getTitle());
		news.setCategory(request.getCategory());
		news.setPublished(request.isPublished());

		return repository.save(news);
	}

	@Override
	public void delete(int newsId) {

		News news = repository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("Not found the news"));

		repository.delete(news);
	}

	@Override
	public News increaseViews(int newsId) {

		News news = repository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("Not found the news"));

		news.setViews(news.getViews() + 1);

		return repository.save(news);
	}

	@Override
	public PaginationResponse getByFilters(LocalDateTime startDate, LocalDateTime endDate,Boolean published ,
			String keyword,String sortBy ,int pageNumber) {
		Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
		if(sortBy!= null && sortBy.equals("lastest")) {
			paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
		} else if(sortBy!= null && sortBy.equals("bestviews")) {
			paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "views"));
		}

		Page<News> pageTuts;
		
		pageTuts = repository.findByFilters(startDate, endDate,published,  keyword, paging);
		
		List<NewsDTO> newsDto = pageTuts.getContent().stream().map(news -> mapToNewsDto(news)).toList();

		return PaginationResponse.builder().currentPage(pageTuts.getNumber()).totalItems(pageTuts.getTotalElements())
				.data(newsDto).totalPages(pageTuts.getTotalPages()).build();
	}

	@Override
	public List<News> getTop5ByView() {
		
		return repository.findTop5ByViews();
	}

	@Override
	public List<News> getAll() {
		
		return repository.findAll();
	}

}
