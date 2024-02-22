package com.danglich.bantra.service;

import java.time.LocalDateTime;
import java.util.List;

import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.model.Contact;

public interface ContactService {
	
	Contact create(Contact request);
	
	List<Contact> getAll();
	
	PaginationResponse getByFilters(LocalDateTime startDate, LocalDateTime endDate, int pageNumber); 

}
