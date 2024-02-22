package com.danglich.bantra.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	@JsonIgnore
	private ProductCategory category;
	
	@NotEmpty(message = "Requires at least one property")
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductProperty> properties;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductMedia> medias;
	
	@NotEmpty(message = "Requires at least one product information")
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductInfo> informations;
	
	//@NotEmpty(message = "Requires at least one variation")
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"product"})
	private List<ProductVariation> variations;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	@NotNull
	@NotBlank(message = "Name is require")
	@Column(name = "name")
	private String name;
	
	@NotNull
	@NotBlank(message = "Description is require")
	@Column(name = "description")
	private String description;
	
	@NotNull
	@NotBlank(message = "SKU code is require")
	@Column(name = "SKU")
	private String sku;
	
	@NotNull
	@NotBlank(message = "Thumbnail is require")
	@Column(name = "thumbnail")
	private String thumbnail;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "likedProduct" , cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	private List<User> likes;
	
	@Column(name = "sold_number")
	private int soldNumber;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "lowest_price")
	private float lowestPrice;
	
	@Column(name = "highest_price")
	private float highestPrice;
	
	@PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = LocalDateTime.now();
        
        
        float lowestPrice = 0;
		float highestPrice = 0;
		for (ProductVariation r : variations) {
			if(variations.indexOf(r) == 0) lowestPrice = r.getPrice();
			lowestPrice = Math.min(lowestPrice, r.getPrice());
			highestPrice = Math.max(highestPrice, r.getPrice());
		}
		
		this.lowestPrice = lowestPrice;
		this.highestPrice = highestPrice;
        
    }
	
	@PreUpdate
	protected void preUpdate() {
		float lowestPrice = 0;
		float highestPrice = 0;
		for (ProductVariation r : variations) {
			if(variations.indexOf(r) == 0) lowestPrice = r.getPrice();
			lowestPrice = Math.min(lowestPrice, r.getPrice());
			highestPrice = Math.max(highestPrice, r.getPrice());
		}
		
		this.lowestPrice = lowestPrice;
		this.highestPrice = highestPrice;
	}
	
	
	public void setInformations(List<ProductInfo> infos) {
		infos.stream().forEach(info  -> info.setProduct(this));
		
		this.informations = infos;
		
	}
	
	public void setProperties(List<ProductProperty> properties) {
		properties.stream().forEach(info  -> info.setProduct(this));
		
		this.properties = properties;
		
	}
	
	public void setVariations(List<ProductVariation> variations) {
		variations.stream().forEach(info  -> info.setProduct(this));
		
		this.variations = variations;
		
	}
	
}
