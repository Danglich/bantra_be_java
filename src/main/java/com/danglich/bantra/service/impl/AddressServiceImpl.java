package com.danglich.bantra.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.danglich.bantra.dto.AddressRequest;
import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.Address;
import com.danglich.bantra.repository.AddressRepository;
import com.danglich.bantra.service.AddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{
	
	private final AddressRepository repository;
	private final ModelMapper mapper;

	@Override
	public Address create(AddressRequest request) {
		Address address = new Address();
		
		mapper.map(request, address);
		return repository.save(address);
	}

	@Override
	public Address update(Address request) {
		Address address = repository.findById(request.getId())
				.orElseThrow(() -> new ResourceNotFoundException("The address is not found"));
		
		mapper.map(request, address);
		return repository.save(address);
	}

	@Override
	public void delete(int theId) {
		Address address = repository.findById(theId)
				.orElseThrow(() -> new ResourceNotFoundException("The address is not found"));
		
		repository.delete(address);
		
	}

}
