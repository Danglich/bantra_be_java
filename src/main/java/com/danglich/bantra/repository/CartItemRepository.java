package com.danglich.bantra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danglich.bantra.model.CartItem;
import com.danglich.bantra.model.CartItemKey;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemKey>{
	
	List<CartItem> findByUserId(int userId);
}
