package com.danglich.bantra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danglich.bantra.model.NewsCategory;

public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Integer>{
	
	List<NewsCategory> findBySlug(String slug);
	

}
