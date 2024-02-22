package com.danglich.bantra.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties(value = {"addresses", "gender",
            "phoneNumber",
            "registered",
            "active",
            "role", 
            "likedProduct"})
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;
	
	@OneToOne
	@JoinColumn(name = "payment_id")
	private PaymentType payment;
	
	@ManyToOne
	@JoinColumn(name = "shipping_id")
	private ShippingMethod shippingMethod;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> items;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "status" , columnDefinition = "ENUM('PENDING', 'SENDING', 'SENT', 'CANCELLED')")
    @Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "sent_at")
	private LocalDateTime sentAt;
	
	@Column(name = "total")
	private float total;
	
	
	@PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = LocalDateTime.now();
        
        // set total
        List<OrderItem> items = this.items;
		float total = 0;
		
		for (OrderItem orderItem : items) {
			total += orderItem.getQuantity() * orderItem.getProductItem().getPrice();
		}
		
		total += this.shippingMethod.getPrice();
		
		this.total = total;
    }
	
}
