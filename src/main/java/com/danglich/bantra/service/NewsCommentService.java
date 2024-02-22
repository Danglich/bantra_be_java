package com.danglich.bantra.service;

import java.util.List;

import com.danglich.bantra.dto.NewsCommentDTO;
import com.danglich.bantra.model.NewsComment;

public interface NewsCommentService {
	
	NewsComment create(NewsCommentDTO request);
	
	void delete(int theId);
	
	List<NewsComment> getChildComments(int parentId);
	
	List<NewsComment> getTopLevelComments(int newsId);

}
