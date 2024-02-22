package com.danglich.bantra.service;

import java.util.List;

import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.ProductDto;
import com.danglich.bantra.dto.UpdatePasswordDTO;
import com.danglich.bantra.dto.UpdateUserDetailDTO;
import com.danglich.bantra.model.User;

public interface UserService {
	
	User getById(int user_id);
	
	User getCurrentUser();
	
	User updateDetail(UpdateUserDetailDTO request);
	
	User updatePassword(UpdatePasswordDTO request);
	
	PaginationResponse getByFilters(Boolean active ,String sort , String role , String keyword, int pageNumber ) throws IllegalAccessException;
	
	// Admin
	User disable(int theId);
	
	void delete(int theId);
	
	User likeProduct(int productId);
	
	List<ProductDto> getLikedProducts();
	
	

}
