package com.danglich.bantra.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.danglich.bantra.dto.AddressRequest;
import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.Address;
import com.danglich.bantra.model.User;
import com.danglich.bantra.model.UserAddress;
import com.danglich.bantra.repository.UserAddressRepository;
import com.danglich.bantra.service.UserAddressService;
import com.danglich.bantra.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService{
	
	private final UserAddressRepository repository;
	private final UserService userService;
	private final ModelMapper mapper;

	@Override
	public UserAddress create(AddressRequest request) {
		
		User user = userService.getCurrentUser();
		
		Address address = mapper.map(request, Address.class);
		
		UserAddress userAddress = repository.save(UserAddress
												.builder()
												.address(address)
												.user(user)
												.isDefault(false)
												.build());
		return repository.save(userAddress);
	}

	@Override
	public UserAddress update(int theId , AddressRequest request) {
		UserAddress userAddress = repository.findById(theId)
									.orElseThrow(() -> new ResourceNotFoundException("Not found user address"));
		
		Address address = mapper.map(request, Address.class);
		userAddress.setAddress(address);
		
		return repository.save(userAddress);
	}

	@Override
	public List<UserAddress> getByUserId(int userId) {
		
		return repository.findByUserId(userId);
	}

	@Override
	public UserAddress setDefault(int theId) {
		
		UserAddress userAddress = repository.findById(theId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found user address"));
		
		userAddress.setDefault(true);
		
		return repository.save(userAddress);
	}

	@Override
	public void delete(int theId) {
		UserAddress userAddress = repository.findById(theId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found user address"));
		
		
		repository.delete(userAddress);
		
	}

}
