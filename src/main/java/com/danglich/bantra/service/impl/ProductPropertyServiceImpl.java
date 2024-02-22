package com.danglich.bantra.service.impl;

import org.springframework.stereotype.Service;

import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.Product;
import com.danglich.bantra.model.ProductProperty;
import com.danglich.bantra.repository.ProductPropertyRepository;
import com.danglich.bantra.repository.ProductRepository;
import com.danglich.bantra.service.ProductPropertyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductPropertyServiceImpl implements ProductPropertyService{
	
	private final ProductPropertyRepository repository;
	private final ProductRepository productRepository;

	@Override
	public ProductProperty create(int productId , ProductProperty request) {
		Product product =  productRepository.findById(productId)
								.orElseThrow(() -> new ResourceNotFoundException("Not found product"));
		
		request.setId(0);
		request.setProduct(product);
		return repository.save(request);
	}

	@Override
	public ProductProperty update(ProductProperty request) {
		
		ProductProperty productProperty = repository.findById(request.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Not found product property"));
		
		productProperty.setName(request.getName());
		productProperty.setValue(request.getValue());
		
		return repository.save(productProperty);
	}

	@Override
	public void delete(int theId) {
		ProductProperty productProperty = repository.findById(theId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found product property"));
		
		repository.delete(productProperty);
		
	}

}
