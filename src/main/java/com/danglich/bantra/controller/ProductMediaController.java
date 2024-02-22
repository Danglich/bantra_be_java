package com.danglich.bantra.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.model.ProductMedia;
import com.danglich.bantra.service.ProductMediaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductMediaController {
	
	// end points
	// POST /admin/products/{product_id}/product_medias : upload media
	// GET /product_medias/{id}  : get file
	// DELETE /admin/product_medias/{id}
	
	private final ProductMediaService service;
	
	@PostMapping("/admin/products/{product_id}/product_medias")
	public ResponseEntity<ProductMedia> upload(@PathVariable("product_id") int productId , @RequestBody MultipartFile file) throws IOException {
		
		return ResponseEntity.ok(service.upload(productId, file));
	}
	
	@GetMapping("/product_medias/{id}")
	public ResponseEntity<byte[]> getById(@PathVariable(name = "id") int id) {
		
		ProductMedia productMedia = service.getById(id);
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(productMedia.getData());
	}
	
	
	@DeleteMapping("/admin/product_medias/{id}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable("id") int theId) {
		
		service.delete(theId);
		
		return ResponseEntity.ok(SuccessResponse.builder()
					.message("Delete succesfully !")
					.status(HttpStatus.OK.value())
					.build());
					
	}

}
