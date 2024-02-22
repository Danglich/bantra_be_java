package com.danglich.bantra.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "full_name")
	private String fullName ;
	
	@Column(name = "email")
	private String email;
	
	@OneToMany(mappedBy = "user")
	private List<UserAddress> addresses;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<CartItem> cartItems;
	
	@Column(name = "gender")
	private boolean gender;
	
	@Column(name = "password")
	@JsonIgnore
	private String password;
	
	@Column(name = "provider")
	private String provider;
	
	@Column(name = "provider_id")
	private String providerId;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "is_registered")
	private boolean registered;
	
	@Column(name = "is_active")
	private boolean active;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@OneToMany(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST , CascadeType.REFRESH})
	@JsonIgnore
	private List<Order> orders;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Review> reviews;
	
	@PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = LocalDateTime.now();
    }
	
	@Column(name = "role" , columnDefinition = "ENUM('USER', 'ADMIN', 'AUTHOR')")
    @Enumerated(EnumType.STRING)
	private Role role;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
	  name = "product_like", 
	  joinColumns = @JoinColumn(name = "user_id"), 
	  inverseJoinColumns = @JoinColumn(name = "product_id"))
	@JsonIgnore
	private List<Product> likedProduct;

	public void likeProduct(Product product) {
		if(likedProduct == null) {
			likedProduct = new ArrayList<>();
		}
		
		if(likedProduct.contains(product)) {
			// dislike
			likedProduct.remove(product);
		} else {
			likedProduct.add(product);
			
		}
		
	}
	
	

}
