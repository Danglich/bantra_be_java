package com.danglich.bantra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id")
//	private int id;
//	
	
	@EmbeddedId
	@JsonIgnore
    CartItemKey id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@MapsId("userId")
	@JsonIgnore
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@MapsId("productId")
	@JsonIgnoreProperties(value = {"quantity", "soldNumber",})
	private ProductVariation productItem;
	
	
	@Column(name = "quantity")
	private int quantity;
}
