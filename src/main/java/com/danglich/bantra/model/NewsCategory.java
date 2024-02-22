package com.danglich.bantra.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "news_category")
@Data
public class NewsCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
//	
//	@OneToMany(mappedBy = "category")
//	private List<News> news ;
	
	@Column(name = "name")
	@NotNull(message = "Name is require")
	@NotBlank(message = "Name is require")
	private String name;
	
	@Column(name = "slug")
	private String slug;
	
	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private List<News> news;
	
}
