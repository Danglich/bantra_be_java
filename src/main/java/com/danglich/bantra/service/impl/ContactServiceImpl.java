package com.danglich.bantra.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.model.Contact;
import com.danglich.bantra.model.Review;
import com.danglich.bantra.repository.ContactRepository;
import com.danglich.bantra.service.ContactService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService{
	
	private final ContactRepository repository;
	
	@Value("${pagination.size}")
	private int pageSize;

	@Override
	public Contact create(Contact request) {
		
		request.setId(0);
		
		return repository.save(request);
	}

	@Override
	public List<Contact> getAll() {
		
		return repository.findAll();
	}

	@Override
	public PaginationResponse getByFilters(LocalDateTime startDate, LocalDateTime endDate, int pageNumber) {
		Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

		Page<Contact> pageTuts;
		
		pageTuts = repository.findByFilters(startDate, endDate, paging);
		
		List<Contact> reviews = pageTuts.getContent();

		return PaginationResponse.builder().currentPage(pageTuts.getNumber()).totalItems(pageTuts.getTotalElements())
				.data(reviews).totalPages(pageTuts.getTotalPages()).build();
	}

}
