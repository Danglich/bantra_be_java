package com.danglich.bantra.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.danglich.bantra.model.News;
import com.danglich.bantra.model.Role;
import com.danglich.bantra.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);
	
	Page<User> findByActive(Boolean active, Pageable page);
	
	@Query("SELECT u FROM User u WHERE " +
            "(:role IS NULL OR u.role = :role) AND " +
            "(:active IS NULL OR u.active = :active) AND " +
            "(:keyword IS NULL OR u.fullName LIKE %:keyword% OR u.email LIKE %:keyword%)")
    Page<User> findByFilters(@Param("role") Role role,
                             @Param("active") Boolean active,
                             @Param("keyword") String keyword,
                             Pageable page);

}
