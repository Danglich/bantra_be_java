package com.danglich.bantra.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.NewsCategory;
import com.danglich.bantra.repository.NewsCategoryRepository;
import com.danglich.bantra.service.NewsCategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsCategoryServiceImpl implements NewsCategoryService{
	
	private final NewsCategoryRepository repository;

	@Override
	public NewsCategory create(NewsCategory request) {
		request.setId(0);
		
		return repository.save(request);
	}

	@Override
	public NewsCategory update(NewsCategory request) {
		NewsCategory category = repository.findById(request.getId())
									.orElseThrow(() -> new ResourceNotFoundException("Not found new category"));
		category.setName(request.getName());
		
		return repository.save(category);
	}

	@Override
	public List<NewsCategory> getAll() {
		
		return repository.findAll();
	}

	@Override
	public void delete(int theId) {
		NewsCategory category = repository.findById(theId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found new category"));

		repository.delete(category);
		
	}

	@Override
	public NewsCategory getBySlug(String slug) {
		
		return repository.findBySlug(slug).get(0);
	}

}
