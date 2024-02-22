package com.danglich.bantra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danglich.bantra.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

}
