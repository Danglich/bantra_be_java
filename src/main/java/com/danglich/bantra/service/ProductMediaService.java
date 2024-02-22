package com.danglich.bantra.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.danglich.bantra.model.ProductMedia;

public interface ProductMediaService {
	
	ProductMedia upload(int productId , MultipartFile file) throws IOException;
	
	ProductMedia getById(int id);
	
	//List<FileDB> getByProductId(int productId);
	
	void delete(int id);
	
}
