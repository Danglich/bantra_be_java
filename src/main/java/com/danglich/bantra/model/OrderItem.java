package com.danglich.bantra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private ProductVariation productItem;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonIgnore
	private Order order;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "reviewed")
	private boolean reviewed;
	
	@OneToOne(mappedBy = "orderItem")
	@JsonIgnore
	private Review review;
	
	@PrePersist
	public void prePersist() {
		this.reviewed = false;
	}
}
