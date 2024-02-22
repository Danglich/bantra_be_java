package com.danglich.bantra.service.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.ProductDto;
import com.danglich.bantra.dto.ProductRequest;
import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.Product;
import com.danglich.bantra.model.ProductVariation;
import com.danglich.bantra.model.Review;
import com.danglich.bantra.repository.ProductCategoryRepository;
import com.danglich.bantra.repository.ProductRepository;
import com.danglich.bantra.service.OrderService;
import com.danglich.bantra.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

	private ProductCategoryRepository productCategoryRepository;
	private ProductRepository productRepository;
	private ModelMapper mapper;
	
	
	
	
	@Value("${pagination.size}")
	private int size;
	
	public ProductDto mapToProductDto(Product product) {
		int totalQuantity = 0;
		int sumRate = 0;
		
		List<Review> reviews = product.getReviews();
		int reviewNumber = reviews.size();
		
		for (Review review : reviews) {
			sumRate += review.getRate();
		}
		
		float rate = 0 ;
		
		if(reviewNumber > 0) rate = sumRate / reviewNumber;
		
		List<ProductVariation> variations = product.getVariations();
		
		for (ProductVariation r : variations) {
			totalQuantity += r.getQuantity();
		}
		
		return ProductDto.builder()
						.id(product.getId())
						.lowestPrice(product.getLowestPrice())
						.highestPrice(product.getHighestPrice())
						.rate(rate)
						.reviewNumber(reviewNumber)
						.name(product.getName())
						.thumbnail(product.getThumbnail())
						.sku(product.getSku())
						.soldNumber(product.getSoldNumber())
						.quantity(totalQuantity)
						.build();
	}

	// Create new product
	public Product createProduct(int categoryId, ProductRequest request) {

		Product productRequest = new Product();
		mapper.map(request, productRequest);

		Product product = productCategoryRepository.findById(categoryId).map(category -> {
			productRequest.setCategory(category);

			return productRepository.save(productRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found category by id : " + categoryId));

		return product;
	}

	// Get all product
	public PaginationResponse getAll(String keyword ,String sku ,Integer categoryId ,int pageNumber, String sortString) {
		
		
		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		
		if(sortString != null) {
			if(sortString.equals("oldest")) sort = Sort.by(Sort.Direction.ASC, "createdAt");
			
			if(sortString.equals("bestseller")) sort = Sort.by(Sort.Direction.DESC, "soldNumber");
			
		}
		
		Pageable paging = PageRequest.of(pageNumber, size, sort);
		
		Page<Product> productsPage ;
		
//		if(keyword == null) {
//			productsPage = productRepository.findAll( paging);
//		} else {
//			productsPage = productRepository.findByNameLike(keyword, paging);
//		}
		
		productsPage = productRepository.findByFilters(keyword, sku, categoryId,  paging);
		
		
		List<ProductDto> result = productsPage.getContent().stream().map(p -> mapToProductDto(p)).toList();
		
		return PaginationResponse.builder()
					.currentPage(productsPage.getNumber())
					.totalItems(productsPage.getTotalElements())
					.data(result)
					.totalPages(productsPage.getTotalPages())
					.build();
	}

	// Get product by category
	public PaginationResponse getByCategory(Integer categoryId ,String keyword ,int pageNumber, String sortString, Float minPrice, Float maxPrice) {

		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		
		if(sortString != null) {
			if(sortString.equals("oldest")) sort = Sort.by(Sort.Direction.ASC, "createdAt");
			
			if(sortString.equals("bestseller")) sort = Sort.by(Sort.Direction.DESC, "soldNumber");
			
		}
		
		Pageable paging = PageRequest.of(pageNumber, size, sort);
		
		Page<Product> productsPage = productRepository.findByCategoryAndFilters(categoryId, keyword, minPrice, maxPrice, paging);
		
		List<ProductDto> result = productsPage.getContent().stream().map(p -> mapToProductDto(p)).toList();
		
		return PaginationResponse.builder()
					.currentPage(productsPage.getNumber())
					.totalItems(productsPage.getTotalElements())
					.data(result)
					.totalPages(productsPage.getTotalPages())
					.build();
	}

	// delete product by id
	public void deleteById(int productId) {

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found product !"));

		productRepository.delete(product);
	}

	// delete product by category

	@Transactional
	public long deleteByCategory(int categoryId) {
		productCategoryRepository.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Not found product category !"));
		
		return productRepository.deleteByCategoryId(categoryId);
	}
	
	
	public Product update(Product productRequest) {
		int productId = productRequest.getId();
		
		Product product = productRepository.findById(productId)
								.orElseThrow(() -> new ResourceNotFoundException("Not found product !"));
		
		product.setName(productRequest.getName());
		product.setSku(productRequest.getSku());
		product.setDescription(productRequest.getDescription());
		product.setThumbnail(productRequest.getThumbnail());
		
		return productRepository.save(product);
	}

	public Product getById(int productId) {
		
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ResourceNotFoundException("Not found product!"));
		 
		return product;
	}

	@Override
	public List<ProductDto> getTopProduct() {
		List<Product> products = productRepository.findTop10ByOrderBySoldNumberDesc();
		
		return products.stream().map(p -> mapToProductDto(p)).toList();
	}

	@Autowired
	public ProductServiceImpl(ProductCategoryRepository productCategoryRepository, ProductRepository productRepository,
			ModelMapper mapper, @Lazy OrderService orderService) {
		super();
		this.productCategoryRepository = productCategoryRepository;
		this.productRepository = productRepository;
		this.mapper = mapper;
	}

	

	

	


	

	
	

}
