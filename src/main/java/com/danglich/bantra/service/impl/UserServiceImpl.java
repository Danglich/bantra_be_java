package com.danglich.bantra.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.ProductDto;
import com.danglich.bantra.dto.UpdatePasswordDTO;
import com.danglich.bantra.dto.UpdateUserDetailDTO;
import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.Product;
import com.danglich.bantra.model.Role;
import com.danglich.bantra.model.User;
import com.danglich.bantra.repository.OrderRepository;
import com.danglich.bantra.repository.UserRepository;
import com.danglich.bantra.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ProductServiceImpl productService;
	private final OrderRepository orderRepository;
	
	@Value("${pagination.size}")
	private int size;
	

	@Override
	public User getById(int userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found user"));
		
		return user;
	}

	@Override
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String userEmail = auth.getPrincipal() != null ? auth.getPrincipal().toString() : "";
		
		return userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("Not found user"));
	}

	@Override
	public User updateDetail(UpdateUserDetailDTO request) {
		User currentUser = getCurrentUser();
		
		if(!currentUser.getEmail().equals(request.getEmail())) {
			throw new IllegalArgumentException("Email invalid");
		}
		
		currentUser.setFullName(request.getFullName());
		currentUser.setGender(request.getGender());
		currentUser.setPhoneNumber(request.getPhoneNumber());
		
		return userRepository.save(currentUser);
	}

	@Override
	public User updatePassword(UpdatePasswordDTO request) {
		
		if(!request.getNewPassword().equals(request.getConfirmNewPassword())) {
			throw new IllegalArgumentException("The two new passwords must be the same");
		}
		
		User currentUser = getCurrentUser();
		
		if(!passwordEncoder.matches(request.getOldPassword(), currentUser.getPassword())) {
			throw new IllegalArgumentException("The password invalid");
		}
		
		currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
		
		return userRepository.save(currentUser);
	}

	@Override
	public PaginationResponse getByFilters(Boolean active ,String sort,String roleString, String keyword , int pageNumber) throws IllegalAccessException {
		
		Sort sortS = Sort.by(Direction.DESC, "createdAt");
		if(sort!= null && sort.equals("oldest")) {
			sortS =Sort.by(Direction.ASC, "createdAt");
		}
		
		Pageable paging = PageRequest.of(pageNumber, size, sortS);
		
		Page<User> pageTuts = null;
		
		Role role = null;
		
		if(roleString != null) {
			try {
				role = Role.valueOf(roleString);
			} catch (IllegalArgumentException e) {
				throw new IllegalAccessException("Status invalid");
			}
			
		}
		
		pageTuts = userRepository.findByFilters(role, active, keyword, paging);
		
		
//		if(active != null) {
//			pageTuts = userRepository.findByActive(active, paging);
//		} else {
//			pageTuts = userRepository.findAll(paging);
//		}
		
		return PaginationResponse.builder()
				.currentPage(pageTuts.getNumber())
				.totalItems(pageTuts.getTotalElements())
				.data(pageTuts.getContent())
				.totalPages(pageTuts.getTotalPages())
				.build();
	}

	@Override
	public User disable(int theId) {
		
		User theUser = userRepository.findById(theId)
							.orElseThrow(() -> new ResourceNotFoundException("User is not found"));
		theUser.setActive(!theUser.isActive());
		
		return userRepository.save(theUser);
	}

	@Override
	@Transactional
	public void delete(int theId) {
		User theUser = userRepository.findById(theId)
				.orElseThrow(() -> new ResourceNotFoundException("User is not found"));
		
		orderRepository.updateOrdersWithNullUser(theId);
		userRepository.delete(theUser);
		
	}

	@Override
	public User likeProduct(int productId) {
		User currentUser = getCurrentUser();
		Product product = productService.getById(productId);
		
		currentUser.likeProduct(product);
		
		return userRepository.save(currentUser);
		
	}

	@Override
	public List<ProductDto> getLikedProducts() {
		User currentUser = getCurrentUser();
		
		List<Product> likedProducts = currentUser.getLikedProduct();
		
		return likedProducts.stream().map(p -> productService.mapToProductDto(p)).toList();
	}

}
