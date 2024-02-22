package com.danglich.bantra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danglich.bantra.model.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, Integer>{

	List<UserAddress> findByUserId(int userId);
}
