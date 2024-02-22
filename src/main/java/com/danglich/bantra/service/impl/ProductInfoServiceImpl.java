package com.danglich.bantra.service.impl;

import org.springframework.stereotype.Service;

import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.Product;
import com.danglich.bantra.model.ProductInfo;
import com.danglich.bantra.repository.ProductInfoRepository;
import com.danglich.bantra.repository.ProductRepository;
import com.danglich.bantra.service.ProductInfoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductInfoServiceImpl implements ProductInfoService{
	
	private final ProductInfoRepository repository;
	private final ProductRepository productRepository;

	@Override
	public ProductInfo create(int productId , ProductInfo request) {
		Product product =  productRepository.findById(productId)
								.orElseThrow(() -> new ResourceNotFoundException("Not found product"));
		
		request.setId(0);
		request.setProduct(product);
		return repository.save(request);
	}

	@Override
	public ProductInfo update(ProductInfo request) {
		
		ProductInfo productInfo = repository.findById(request.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Not found product information"));
		
		productInfo.setInformation(request.getInformation());
		
		return repository.save(productInfo);
	}

	@Override
	public void delete(int theId) {
		ProductInfo productInfo = repository.findById(theId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found product information"));
		
		repository.delete(productInfo);
		
	}

}
