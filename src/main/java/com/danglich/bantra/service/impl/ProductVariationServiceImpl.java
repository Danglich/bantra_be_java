package com.danglich.bantra.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.danglich.bantra.exception.OutOfStockExcepion;
import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.Product;
import com.danglich.bantra.model.ProductVariation;
import com.danglich.bantra.repository.ProductRepository;
import com.danglich.bantra.repository.ProductVariationRepository;
import com.danglich.bantra.service.ProductVariationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductVariationServiceImpl implements ProductVariationService{
	
	private final ProductVariationRepository repository;
	private final ProductRepository productRepository;

	@Override
	public ProductVariation create(int productId , ProductVariation request) {
		Product product =  productRepository.findById(productId)
								.orElseThrow(() -> new ResourceNotFoundException("Not found product"));
		
		request.setId(0);
		request.setProduct(product);
		return repository.save(request);
	}

	@Override
	public ProductVariation update(ProductVariation request) {
		
		ProductVariation variation = repository.findById(request.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Not found product variation"));
		
		variation.setName(request.getName());
		variation.setPrice(request.getPrice());
		variation.setQuantity(request.getQuantity());
		
		return repository.save(variation);
	}

	@Override
	public void delete(int theId) {
		ProductVariation productVariation = repository.findById(theId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found product variation"));
		
		repository.delete(productVariation);
		
	}

	@Override
	public ProductVariation updateSoldNumber(int productId, int quantity) {
		
		ProductVariation productVariation = repository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found product!"));
		
		if(quantity > productVariation.getQuantity()) {
			throw new OutOfStockExcepion("This product is out of stock", quantity);
		}
		
		productVariation.setSoldNumber(quantity + productVariation.getSoldNumber());
		
		Product product = productVariation.getProduct();
		product.setSoldNumber(product.getSoldNumber() + quantity);
		
		productRepository.save(product);
		
		return repository.save(productVariation);
	}

	@Override
	public List<ProductVariation> getByProductId(int productId) {
		
		return repository.findByProductId(productId);
	}

	

}
