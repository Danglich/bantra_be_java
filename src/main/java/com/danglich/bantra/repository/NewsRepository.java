package com.danglich.bantra.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danglich.bantra.model.News;

public interface NewsRepository extends JpaRepository<News, Integer>{
	
	List<News> findByCategoryIdOrderByCreatedAtDesc(int categoryId);
	
	Page<News> findByUserId(int userId, Pageable page);
	
	@Query("SELECT n FROM News n WHERE n.title LIKE %?1% OR n.content LIKE %?2%")
	Page<News> searchByTitleOrContent(String title, String content, Pageable page);
	
	 @Query("SELECT n FROM News n WHERE n.published = TRUE ORDER BY n.views DESC")
	 List<News> findTop5ByViews();
	
	@Query("SELECT n FROM News n WHERE " +
            "(:startDate IS NULL OR n.createdAt >= :startDate) AND " +
            "(:endDate IS NULL OR n.createdAt <= :endDate) AND " +
            "(:published IS NULL OR n.published = :published) AND " +
            "(:keyword IS NULL OR n.title LIKE %:keyword% OR n.content LIKE %:keyword%)")
    Page<News> findByFilters(@Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate,
                             @Param("published") Boolean published,
                             @Param("keyword") String keyword,
                             Pageable page);
}

