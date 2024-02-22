package com.danglich.bantra.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@Table(name = "product_variation")
@Data
public class ProductVariation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinColumn(name = "product_id")
	@JsonIgnoreProperties(value = {"reviews", "variations", "properties" , "informations", "medias"})
	private Product product;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "sold_number")
	private int soldNumber;
	
	@Column(name = "quantity")
	@Min(message = "Quantity must not 0", value = 1)
	private int quantity;
	
	@Min(message = "Price must not 0", value = 1)
	@Column(name = "price")
	private float price;
}
