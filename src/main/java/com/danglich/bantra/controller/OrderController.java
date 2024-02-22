package com.danglich.bantra.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danglich.bantra.dto.OrderRequestDTO;
import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.StatsDTO;
import com.danglich.bantra.dto.SuccessResponse;
import com.danglich.bantra.dto.UpdateOrderDTO;
import com.danglich.bantra.dto.UpdateStatusOrderDTO;
import com.danglich.bantra.model.Order;
import com.danglich.bantra.model.OrderItem;
import com.danglich.bantra.model.OrderStatus;
import com.danglich.bantra.service.OrderService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

	// End points
	// POST  /orders  create order
	// GET   /orders   get all order
	// GET   /orders/reviewable   get all order item reviewable
	// GET   /orders/top   get top 5 lastest order
	// GET   /orders/{id}  get by id
	// GET   /orders/status?status   get by status
	// GET   /users/orders/{user_id}  get by user
	// PUT   /orders                  update
	// PUT   /orders/update/status    update status
	// PUT   /admin/orders/update/status    update status
	// PUT   /users/orders/cancel           cancel order
	// PUT   /admin/orders/cancel           cancel order
	// DELETE  /orders/{order_id}           delete
	
	
	private final OrderService service;
	
	@PostMapping("/orders")
	public ResponseEntity<Order> create(@Valid @RequestBody OrderRequestDTO request) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createByUser(request));
	}
	
	@GetMapping("/orders/{id}")
	public ResponseEntity<Order> getById(@PathVariable(name = "id") int id) {
		
		return ResponseEntity.ok(service.getById(id));
	}
	
	@GetMapping("/orders/reviewable")
	public ResponseEntity<List<OrderItem>> getOrderItemsReviewable() {
		
		return ResponseEntity.ok(service.getReviewableOrderItems());
	}
	
	@GetMapping("/orders/top")
	public ResponseEntity<List<Order>> getTop5() {
		
		return ResponseEntity.ok(service.getTop5());
	}
	
	@GetMapping("/orders/stats")
	public ResponseEntity<List<StatsDTO>> getStats() {
		
		return ResponseEntity.ok(service.getMonthlyRevenue(2023));
	}
	
	@PutMapping("/orders")
	public ResponseEntity<Order> update(@Valid @RequestBody UpdateOrderDTO request) {
		
		return ResponseEntity.ok(service.update(request));
	}
	
	@GetMapping("/orders")
	public ResponseEntity<PaginationResponse> getAll(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "status", required = false) String status) throws IllegalAccessException {
		
		if(status == null || status.equals("ALL")) {
			return ResponseEntity.ok(service.getAll( page));
		}
		
		
		return ResponseEntity.ok(service.getByStatus(status, page));
		
	}
	
	@GetMapping("/orders/status")
	public ResponseEntity<PaginationResponse> getByStatus(@PathParam("status") String status, @RequestParam(name = "page", defaultValue = "0") int page) throws IllegalAccessException {
		
		return ResponseEntity.ok(service.getByStatus(status, page));
	}
	
	@GetMapping("/users/orders")
	public ResponseEntity<List<Order>> getByUser(@RequestParam(name = "status", required = false) String status) {
		
		return ResponseEntity.ok(service.getByUser(status));
	}
	
	@PutMapping("/orders/update/status")
	public ResponseEntity<Order> updateStatusByUser(@Valid @RequestBody UpdateStatusOrderDTO request) throws IllegalAccessException {
		
		return ResponseEntity.ok(service.updateStatusByUser(request));
	}
	

	
	
	@PutMapping("/admin/orders/update/status")
	public ResponseEntity<Order> updateStatusByAdmin(@Valid @RequestBody UpdateStatusOrderDTO request) throws IllegalAccessException {
		
		return ResponseEntity.ok(service.updateStatusAdmin(request));
	}
	
	@PutMapping("/users/orders/cancel")
	public ResponseEntity<Order> cancelByUser(@Valid @RequestBody Order request){
		
		return ResponseEntity.ok(service.cancelByUser(request));
	}
	
	
	@PutMapping("/admin/orders/cancel")
	public ResponseEntity<Order> cancelByAdmin(@Valid @RequestBody Order request){
		
		return ResponseEntity.ok(service.cancelByAdmin(request));
	}
	
	@DeleteMapping("/orders/{order_id}")
	public ResponseEntity<SuccessResponse> deleteById(@PathVariable(name = "order_id") int theId) {
		
		service.delete(theId);
		
		SuccessResponse response = SuccessResponse.builder()
											.message("Delete order succesfully!")
											.status(200)
											.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
