package com.danglich.bantra.service;

import java.util.List;

import com.danglich.bantra.model.NewsCategory;

public interface NewsCategoryService {
	
	NewsCategory create(NewsCategory request);
	NewsCategory update(NewsCategory request);
	List<NewsCategory> getAll();
	void delete(int theId);
	
	NewsCategory getBySlug(String slug);

}
