package com.danglich.bantra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danglich.bantra.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{

}
