package com.danglich.bantra.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.danglich.bantra.dto.NewsCommentDTO;
import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.News;
import com.danglich.bantra.model.NewsComment;
import com.danglich.bantra.model.User;
import com.danglich.bantra.repository.NewsCommentRepository;
import com.danglich.bantra.service.NewsCommentService;
import com.danglich.bantra.service.NewsService;
import com.danglich.bantra.service.UserService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class NewsCommentServiceImpl implements NewsCommentService{
	
	private final NewsCommentRepository repository;
	private final NewsService newsService;
	private final UserService userService;

	@Override
	public NewsComment create(NewsCommentDTO request) {
		News news = newsService.getById(request.getNewsId());
		User user = userService.getCurrentUser();
		Optional<NewsComment> parentComment = Optional.ofNullable(null);
		if(request.getParentId() != null) {
			parentComment = repository.findById(request.getParentId());
			
		}
		
		NewsComment comment = NewsComment.builder()
								.content(request.getContent())
								.news(news)
								.user(user)
								.parentComment(parentComment.isPresent() ? parentComment.get() : null)
								.build();
		
		return repository.save(comment);
	}

	@Override
	public void delete(int theId) {
		User user = userService.getCurrentUser();
		
		NewsComment comment = repository.findById(theId)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bình luận"));
		if(comment.getUser().getEmail() != user.getEmail()) {
			throw new IllegalAccessError("Bạn không được quyền xoá bình luận này");
		}
		
		repository.delete(comment);
	}

	@Override
	public List<NewsComment> getChildComments(int parentId) {
		Optional<NewsComment> optional = repository.findById(parentId);
		
		if(optional.isEmpty()) {
			return new ArrayList<>();
		}
		
		 return repository.findByParentCommentOrderByCreatedAt(optional.get());
	}

	@Override
	public List<NewsComment> getTopLevelComments(int newsId) {
		// TODO Auto-generated method stub
		return repository.findByNewsIdAndParentCommentIsNullOrderByCreatedAt(newsId);
	}

}
