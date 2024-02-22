package com.danglich.bantra.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.FileDB;
import com.danglich.bantra.repository.FileDBRepository;
import com.danglich.bantra.service.FileDBService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileDBServiceImpl implements FileDBService{
	
	private final FileDBRepository fileDBRepository;

	@Override
	public FileDB upload(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		FileDB fileDB = FileDB.builder()
							.data(file.getBytes())
							.name(fileName)
							.type(file.getContentType())
							.build();
		
		return fileDBRepository.save(fileDB);
	}

	@Override
	public FileDB getById(String id) {
		
		
		return fileDBRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The file not found"));
	}

	@Override
	public List<FileDB> getAll() {
		
		return fileDBRepository.findAll();
	}

}
