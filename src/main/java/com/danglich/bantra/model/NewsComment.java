package com.danglich.bantra.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "news_comment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties(value = {"addresses", "gender", "phoneNumber"})
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "news_id")
	@JsonIgnore
	private News news;
	
	@Column(name = "content")
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
	@JsonIgnore
    private NewsComment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NewsComment> childComments;
    
    @Column(name = "created_at")
	private LocalDateTime createdAt;
    
    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = LocalDateTime.now();
    }
	
}
