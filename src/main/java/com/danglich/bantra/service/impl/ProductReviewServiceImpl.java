package com.danglich.bantra.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.danglich.bantra.dto.NewsDTO;
import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.ReviewDto;
import com.danglich.bantra.dto.ReviewRequest;
import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.News;
import com.danglich.bantra.model.Order;
import com.danglich.bantra.model.OrderItem;
import com.danglich.bantra.model.Product;
import com.danglich.bantra.model.Review;
import com.danglich.bantra.model.User;
import com.danglich.bantra.repository.OrderItemRepository;
import com.danglich.bantra.repository.ProductRepository;
import com.danglich.bantra.repository.ReviewRepository;
import com.danglich.bantra.repository.UserRepository;
import com.danglich.bantra.service.ReviewService;
import com.danglich.bantra.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ReviewService{
	
	private final ReviewRepository repository;
	private final ProductRepository productRepository;
	private final UserService userService;
	private final OrderItemRepository orderItemRepository;
	private final ModelMapper mapper;

	
	@Value("${pagination.size}")
	private int pageSize;
	
	@Override
	public Review create(ReviewRequest request) {
		
		User user = userService.getCurrentUser();
		
		OrderItem orderItem =  orderItemRepository.findById(request.getOrderId())
				.orElseThrow(() -> new ResourceNotFoundException("Not found order item"));
		
		Order order  = orderItem.getOrder();
		
		Product product =  orderItem.getProductItem().getProduct();
		
		if(order.getUser() != user) {
			throw new IllegalAccessError("Bạn không có quyền đánh giá");
		}
		
		if(order.getSentAt() == null) {
			throw new IllegalAccessError("Bạn chưa thể đánh giá đơn hàng này");
		}
		
		if(order.getSentAt().isBefore(LocalDateTime.now().minusDays(7))) {
			throw new IllegalAccessError("Bạn đã quá thời gian để đánh giá đơn hàng này");
			
		}
		
		if(orderItem.isReviewed()) {
			throw new IllegalArgumentException("Bạn đã đánh giá đơn hàng này rồi");
		}
		
		Review review = Review.builder()
							.content(request.getContent())
							.orderItem(orderItem)
							.product(product)
							.user(user)
							.rate(request.getRate())
							.build();
		
		// update order item 
		orderItem.setReviewed(true);
		
		orderItemRepository.save(orderItem);
		
		
		
		
		return repository.save(review);
			
	}

	@Override
	public ReviewDto update(Review request) {
		
		Review review = repository.findById(request.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Not found product review"));
		
		review.setContent(request.getContent());
		review.setRate(request.getRate());
		
		ReviewDto response = new ReviewDto();
		mapper.map(repository.save(review), response);
		response.setUsername(repository.save(review).getUser().getFullName());
		
		return response;
	}

	@Override
	public void delete(int theId) {
		Review review = repository.findById(theId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found product review"));
		
		repository.delete(review);
		
	}

	@Override
	public List<Review> getAllOrderByCreatedAt() {
		
		return repository.findByOrderByIdDesc();
	}

	@Override
	public PaginationResponse getByFilters(LocalDateTime startDate, LocalDateTime endDate, Integer rate, int pageNumber) {
		Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

		Page<Review> pageTuts;
		
		pageTuts = repository.findByFilters(startDate, endDate,rate, paging);
		
		List<Review> reviews = pageTuts.getContent();

		return PaginationResponse.builder().currentPage(pageTuts.getNumber()).totalItems(pageTuts.getTotalElements())
				.data(reviews).totalPages(pageTuts.getTotalPages()).build();
	}

	@Override
	public List<Review> getTop5() {
		
		return repository.findFirst5ByOrderByCreatedAtDesc();
	}

	

}
