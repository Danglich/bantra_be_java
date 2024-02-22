package com.danglich.bantra.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	@JsonIgnoreProperties(value = {"properties", "medias", "informations", "variations", "reviews", "description"})
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties(
			value = { "addresses", "cartItems", 
					"gender", "role", "active", "registered",
					"phoneNumber"})
	private User user;
	
	@Column(name = "content")
	@NotBlank(message = "Content is required")
	private String content;
	
	
	@OneToOne
	@JoinColumn(name = "order_id")
	private OrderItem orderItem;
	
	@Column(name = "rate")
	@NotNull(message = "Rate is required")
	@Min(value = 1 , message = "Rate must between 1 - 5")
	@Max(value = 5 , message = "Rate must between 1 - 5")
	private Integer rate;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = LocalDateTime.now();
    }

}
