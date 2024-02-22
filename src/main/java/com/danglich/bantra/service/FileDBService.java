package com.danglich.bantra.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.danglich.bantra.model.FileDB;

public interface FileDBService {
	
	FileDB upload(MultipartFile file) throws IOException;
	
	FileDB getById(String id);
	
	List<FileDB> getAll();

}
