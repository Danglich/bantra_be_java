package com.danglich.bantra.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import com.danglich.bantra.dto.ProductCategoryDto;
import com.danglich.bantra.model.ProductCategory;
import com.danglich.bantra.repository.ProductCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
	
	private final ProductCategoryRepository repository;
	
	@Transactional
	public void save(ProductCategoryDto request) {
		ProductCategory category = 
				ProductCategory.builder()
					.name(request.getName())
					.thumbnail(request.getThumbnail())
					.build();
		
		repository.save(category);
	}
	
	public List<ProductCategory> getAll() {
		
		return repository.findAll();
	}
	
	public ProductCategory findBySlug(String slug) {
		
		return repository.findBySlug(slug).orElseThrow(() -> new ResourceAccessException("Not found category with slug"));
	}

}
