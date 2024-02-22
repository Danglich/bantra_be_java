package com.danglich.bantra.model;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "contact")
public class Contact {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "email")
	@NotBlank(message = "Email is require")
	private String email;
	
	@Column(name = "full_name")
	@NotBlank(message = "Full name is require")
	private String fullName;
	
	@Column(name = "content")
	@NotBlank(message = "Content is require")
	private String content;
	
	@Column(name = "phone_number")
	@NotBlank(message = "Phone number is require")
	private String phoneNumber;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = LocalDateTime.now();
    }

}
