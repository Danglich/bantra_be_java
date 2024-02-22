package com.danglich.bantra.controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.danglich.bantra.model.FileDB;
import com.danglich.bantra.service.FileDBService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
//@CrossOrigin
public class FileDBController {
	
	
	
	private final FileDBService fileDBService;
	
	@PostMapping("/admin/files/upload")
	public ResponseEntity<String> upload(@RequestBody MultipartFile image) throws IOException {
		
		FileDB file =  fileDBService.upload(image);
		
		return ResponseEntity.ok("http://localhost:8080/api/files/" + file.getId());
	}
	
	@GetMapping("/files/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable(name = "id") String id) {
		FileDB fileDB = fileDBService.getById(id);
		
		return ResponseEntity.ok()
		        //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
				.contentType(MediaType.valueOf("image/png"))
		        .body(fileDB.getData());
	}
	
	

}
