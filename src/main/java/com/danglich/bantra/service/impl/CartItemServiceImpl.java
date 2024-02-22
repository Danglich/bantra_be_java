package com.danglich.bantra.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.danglich.bantra.dto.CartItemDTO;
import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.CartItem;
import com.danglich.bantra.model.CartItemKey;
import com.danglich.bantra.model.ProductVariation;
import com.danglich.bantra.model.User;
import com.danglich.bantra.repository.CartItemRepository;
import com.danglich.bantra.repository.ProductVariationRepository;
import com.danglich.bantra.repository.UserRepository;
import com.danglich.bantra.service.CartItemService;
import com.danglich.bantra.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

	private final CartItemRepository repository;
	private final UserService userService;
	private final ProductVariationRepository productRepository;

	@Override
	public CartItem create(CartItemDTO request) {

		User user = userService.getCurrentUser();

		ProductVariation product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Not found product"));

		CartItemKey theKey = CartItemKey.builder().productId(request.getProductId()).userId(user.getId()).build();

		Optional<CartItem> optional = repository.findById(theKey);

		CartItem result = new CartItem();

		if (optional.isPresent()) {
			CartItem cartItem = optional.get();

			int newQuantity = request.getQuantity() + cartItem.getQuantity();

			if (newQuantity <= 0) {
				delete(request.getProductId());
			} else {
				cartItem.setQuantity(newQuantity);

			}
			
			cartItem.setProductItem(product);
			cartItem.setUser(user);

			result = repository.save(cartItem);

		} else {
			result = repository.save(CartItem.builder().id(theKey).productItem(product).user(user).quantity(request.getQuantity()).build());
		}

		return result;
	}

	@Override
	public void delete(int productId) {

		User user = userService.getCurrentUser();
		CartItemKey theKey = CartItemKey.builder().productId(productId).userId(user.getId()).build();
		
		CartItem cartItem = repository.findById(theKey)
				.orElseThrow(() -> new ResourceNotFoundException("Not found cart item"));

		repository.delete(cartItem);

	}

	@Override
	public List<CartItem> findByUserId(int userId) {
		List<CartItem> items = repository.findByUserId(userId);
		
//		List<CartItem> newList = new ArrayList<>();
//		
//		newList = items.stream().map(item -> {
//			return new CartItem();
//		}).toList();
//		
//		System.out.println(items.get(0).getProductItem().getVariation());
		 
		return items;
	}

}
