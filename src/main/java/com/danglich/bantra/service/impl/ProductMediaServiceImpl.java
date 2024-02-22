package com.danglich.bantra.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.FileDB;
import com.danglich.bantra.model.Product;
import com.danglich.bantra.model.ProductMedia;
import com.danglich.bantra.repository.ProductMediaRepository;
import com.danglich.bantra.repository.ProductRepository;
import com.danglich.bantra.service.ProductMediaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductMediaServiceImpl implements ProductMediaService{
	
	private final ProductMediaRepository repository;
	private final ProductRepository productRepository;
	
	
	@Override
	public ProductMedia upload(int productId, MultipartFile file) throws IOException {
		
		Product product = productRepository.findById(productId)
								.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		ProductMedia productMedia = ProductMedia.builder()
										.data(file.getBytes())
										.name(fileName)
										.type(file.getContentType())
										.product(product)
										.build();
		
		return repository.save(productMedia);
	}
	
	
	@Override
	public ProductMedia getById(int id) {
		
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
	}
	
	
	@Override
	public void delete(int id) {
		ProductMedia productMedia = repository.findById(id)
										.orElseThrow(() -> new ResourceNotFoundException("The media not found"));
										
		repository.delete(productMedia);
	}
	
	
}
