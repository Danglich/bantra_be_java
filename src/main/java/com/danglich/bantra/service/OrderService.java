package com.danglich.bantra.service;

import java.util.List;
import java.util.Map;

import com.danglich.bantra.dto.OrderRequestDTO;
import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.StatsDTO;
import com.danglich.bantra.dto.UpdateOrderDTO;
import com.danglich.bantra.dto.UpdateStatusOrderDTO;
import com.danglich.bantra.model.Order;
import com.danglich.bantra.model.OrderItem;
import com.danglich.bantra.model.OrderStatus;
import com.danglich.bantra.model.Product;
import com.danglich.bantra.model.ProductVariation;

public interface OrderService {
	
	Order createByUser(OrderRequestDTO request);
	
	Order getById(int id);
	
	Order update(UpdateOrderDTO request);
	
	Order updateStatusByUser(UpdateStatusOrderDTO request) throws IllegalAccessException;
	
	Order updateStatusAdmin(UpdateStatusOrderDTO request) throws IllegalAccessException;
	
	Order cancelByUser(Order request);
	
	Order cancelByAdmin(Order request);
	
	PaginationResponse getAll(int pageNumber);
	
	List<Order> getByUser(String status);
	
	List<Order> getTop5();
	
	PaginationResponse getByStatus(String status, int pageNumber) throws IllegalAccessException;
	
	void delete(int theId);

	List<StatsDTO> getMonthlyRevenue(int year);
	
	List<OrderItem> getReviewableOrderItems();

}
