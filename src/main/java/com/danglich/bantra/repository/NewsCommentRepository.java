package com.danglich.bantra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danglich.bantra.model.NewsComment;

public interface NewsCommentRepository extends JpaRepository<NewsComment, Integer>{
	
	List<NewsComment> findByNewsIdAndParentCommentIsNullOrderByCreatedAt(int newsId);
	
	List<NewsComment> findByParentCommentOrderByCreatedAt(NewsComment parentComment);
	

}
