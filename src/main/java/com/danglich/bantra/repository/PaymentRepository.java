package com.danglich.bantra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danglich.bantra.model.PaymentType;

public interface PaymentRepository extends JpaRepository<PaymentType, Integer>{

}
