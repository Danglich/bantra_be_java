package com.danglich.bantra.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.PromotionRequest;
import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.ProductCategory;
import com.danglich.bantra.model.Promotion;
import com.danglich.bantra.repository.ProductCategoryRepository;
import com.danglich.bantra.repository.PromotionRepository;
import com.danglich.bantra.service.PromotionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService{
	
	private final PromotionRepository promotionRepository;
	private final ProductCategoryRepository categoryRepository;
	private final ModelMapper mapper;
	
	@Value("${pagination.size}")
	private int size;

	@Override
	public PaginationResponse getAll(int pageNumber, Integer categoryId, String status) {
		
		Pageable paging = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "startDate"));
		Page<Promotion> pageTuts = null;
		
		if(status!= null && status.equals("expired")) {
			pageTuts = promotionRepository.findUnActivePromotionsByProductCategoryId(categoryId, LocalDateTime.now(), paging);
		} else if (status!= null && status.equals("unexpired")) {
			pageTuts = promotionRepository.findActivePromotionsByProductCategoryId(categoryId, LocalDateTime.now(), paging);
		} else {
			pageTuts = promotionRepository.findPromotionsByProductCategoryId(categoryId, paging);
			//pageTuts = promotionRepository.findAll(paging);
		}
		
		return PaginationResponse.builder()
					.currentPage(pageTuts.getNumber())
					.totalItems(pageTuts.getTotalElements())
					.data(pageTuts.getContent())
					.totalPages(pageTuts.getTotalPages())
					.build();
	}

	@Override
	public Promotion create(PromotionRequest request) {
		List<ProductCategory> categories = categoryRepository.findAllById(request.getCategoryIds());
		
		String pattern = "yyyy-MM-dd HH:mm:ss";
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        
        LocalDateTime startDate = LocalDateTime.parse(request.getStartDate() + " 00:00:00", formatter);
        LocalDateTime endDate = LocalDateTime.parse(request.getEndDate() + " 00:00:00", formatter);
		Promotion promotion = Promotion.builder()
								.description(request.getDescription())
								.discountRate(request.getDiscountRate())
								.thumbnail(request.getThumbnail())
								.startDate(startDate)
								.endDate(endDate)
								.productCategories(categories)
								.build();
		
		return promotionRepository.save(promotion);
	}

	@Override
	public Promotion update(PromotionRequest request) {
		
		Promotion promotion = promotionRepository.findById(request.getId())
								.orElseThrow(() -> new ResourceNotFoundException("The promotion is not found"));
		
		List<ProductCategory> categories = categoryRepository.findAllById(request.getCategoryIds());
		String pattern = "yyyy-MM-dd HH:mm:ss";
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        
        LocalDateTime startDate = LocalDateTime.parse(request.getStartDate() + " 00:00:00", formatter);
        LocalDateTime endDate = LocalDateTime.parse(request.getEndDate() + " 00:00:00", formatter);
		
		mapper.map(request, promotion);
		promotion.setEndDate(endDate);
		promotion.setStartDate(startDate);
		promotion.setProductCategories(categories);
		
		return promotionRepository.save(promotion);
	}

	@Override
	public void delete(int theId) {
		Promotion promotion = promotionRepository.findById(theId)
				.orElseThrow(() -> new ResourceNotFoundException("The promotion is not found"));

		promotionRepository.delete(promotion);
		
	}
	
	

}
