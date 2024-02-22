package com.danglich.bantra.service;

import java.util.List;

import com.danglich.bantra.dto.CartItemDTO;
import com.danglich.bantra.model.CartItem;

public interface CartItemService {
	
	CartItem create(CartItemDTO request);
	
	void delete(int productId);
	
	List<CartItem> findByUserId(int userId);

}
