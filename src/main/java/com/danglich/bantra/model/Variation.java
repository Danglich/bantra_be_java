package com.danglich.bantra.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class Variation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@OneToOne(mappedBy = "variation")
	private ProductVariation productItem;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private float price;

}
