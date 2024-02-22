package com.danglich.bantra.service.impl;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.boot.model.source.spi.Sortable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.danglich.bantra.dto.OrderDTO;
import com.danglich.bantra.dto.OrderRequestDTO;
import com.danglich.bantra.dto.PaginationResponse;
import com.danglich.bantra.dto.StatsDTO;
import com.danglich.bantra.dto.UpdateOrderDTO;
import com.danglich.bantra.dto.UpdateStatusOrderDTO;
import com.danglich.bantra.exception.ResourceNotFoundException;
import com.danglich.bantra.model.Address;
import com.danglich.bantra.model.Order;
import com.danglich.bantra.model.OrderItem;
import com.danglich.bantra.model.OrderStatus;
import com.danglich.bantra.model.PaymentType;
import com.danglich.bantra.model.Product;
import com.danglich.bantra.model.ProductVariation;
import com.danglich.bantra.model.ShippingMethod;
import com.danglich.bantra.model.User;
import com.danglich.bantra.repository.AddressRepository;
import com.danglich.bantra.repository.OrderRepository;
import com.danglich.bantra.repository.PaymentRepository;
import com.danglich.bantra.repository.ProductVariationRepository;
import com.danglich.bantra.repository.ShippingMethodRepository;
import com.danglich.bantra.service.OrderService;
import com.danglich.bantra.service.ProductVariationService;
import com.danglich.bantra.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository repository;
	private final UserService userService;
	private final AddressRepository addressRepository;
	private final ShippingMethodRepository shippingMethodRepository;
	private final PaymentRepository paymentRepository;
	private final ProductVariationRepository productVariationRepository;
	private final ProductVariationService productVariationService;

	@Value("${pagination.size}")
	private int size;
	
	private OrderDTO mapToDTO(Order request) {
		
		int total = 0;
		
		List<OrderItem> items = request.getItems();
		
		for (OrderItem orderItem : items) {
			total += orderItem.getQuantity() * orderItem.getProductItem().getPrice();
		}
		
		total += request.getShippingMethod().getPrice();
		
		return OrderDTO.builder()
				.createdAt(request.getCreatedAt())
				.sentAt(request.getSentAt())
				.id(request.getId())
				.status(request.getStatus().name())
				.paymentMethod(request.getPayment().getName())
				.shippingMethod(request.getShippingMethod().getName())
				.address(request.getAddress().getProvince())
				.total(total)
				.build();
	}

	@Override
	public Order createByUser(OrderRequestDTO request) {
		Order theOrder = new Order();

		User user = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal() != null) {
			user = userService.getCurrentUser();
		}

		Address address = addressRepository.findById(request.getAddressId())
				.orElseThrow(() -> new ResourceNotFoundException("The address is not found"));

		ShippingMethod shippingMethod = shippingMethodRepository.findById(request.getShippingId())
				.orElseThrow(() -> new ResourceNotFoundException("The shipping method is not found"));

		PaymentType paymentType = paymentRepository.findById(request.getPaymentId())
				.orElseThrow(() -> new ResourceNotFoundException("The payment type is not found"));

		List<OrderItem> orderItems = request.getItems().stream().map(itemDTO -> {

			OrderItem orderItem = new OrderItem();

			ProductVariation product = productVariationRepository.findById(itemDTO.getProductId())
					.orElseThrow(() -> new ResourceNotFoundException("Product variation is not found"));

			orderItem.setProductItem(product);
			orderItem.setOrder(theOrder);
			orderItem.setQuantity(itemDTO.getQuantity());

			return orderItem;

		}).toList();

		theOrder.setItems(orderItems);
		theOrder.setUser(user);
		theOrder.setNote(request.getNote());
		theOrder.setPayment(paymentType);
		theOrder.setShippingMethod(shippingMethod);
		theOrder.setAddress(address);
		theOrder.setStatus(OrderStatus.PENDING);

		return repository.save(theOrder);
	}

	@Override
	public PaginationResponse getAll(int pageNumber) {
		Pageable paging = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "createdAt"));

		Page<Order> pageTuts = repository.findAll(paging);
		
		List<OrderDTO> result = pageTuts.getContent().stream().map(o -> mapToDTO(o)).toList();

		return PaginationResponse.builder().currentPage(pageTuts.getNumber()).totalItems(pageTuts.getTotalElements())
				.data(result).totalPages(pageTuts.getTotalPages()).build();

	}

	@Override
	public List<Order> getByUser(String statusString) {
		User currentUser = userService.getCurrentUser();
		
		OrderStatus status = null;
		
		if(statusString != null) {
			try {
				status = OrderStatus.valueOf(statusString);
			} catch (Exception e) {
				throw new IllegalArgumentException("Status invalid");
			}
			
		}

		return repository.findByUserIdAndStatus(currentUser.getId(), status);
	}

	@Override
	public void delete(int theId) {

		Order theOrder = repository.findById(theId)
				.orElseThrow(() -> new ResourceNotFoundException("The order is not found"));

		repository.delete(theOrder);

	}

	@Override
	public Order updateStatusByUser(UpdateStatusOrderDTO request) throws IllegalAccessException {

		Order theOrder = repository.findById(request.getOrderId())
				.orElseThrow(() -> new ResourceNotFoundException("The order is not found"));
		User user = userService.getCurrentUser();
		
		if(user != theOrder.getUser()) {
			throw new IllegalAccessError("Bạn không được phép thay đổi đơn hàng này");
		}
		
		if(!theOrder.getStatus().name().equals("PENDING") && !theOrder.getStatus().name().equals("CANCELLED")) {
			throw new IllegalAccessException("Bạn không được phép thay đổi trạng thái này");
		}
		
		OrderStatus theStatus = null;
		try {
			theStatus = OrderStatus.valueOf(request.getStatus());
		} catch (IllegalArgumentException e) {
			throw new IllegalAccessException("Status invalid");
		}
		
		if(theStatus.name().equals("PENDING") || theStatus.name().equals("CANCELLED")) {
			if(theStatus.name().equals("PENDING")) {
				theOrder.setCreatedAt(LocalDateTime.now());
			}
			theOrder.setStatus(theStatus);
			
		} else {
			throw new IllegalAccessError("Bạn không được phép thay đổi trạng thái đó");
		}

		return repository.save(theOrder);
	}

	
	@Override
	public Order updateStatusAdmin(UpdateStatusOrderDTO request) throws IllegalAccessException {
		Order theOrder = repository.findById(request.getOrderId())
				.orElseThrow(() -> new ResourceNotFoundException("The order is not found"));
		
		if(theOrder.getStatus().name().equals("SENT")) {
			throw new IllegalAccessException("Status is not change");
		}
		
		if(theOrder.getStatus().name().equals("CANCELLED")) {
			throw new IllegalAccessException("Đơn hàng đã bị hủy , bạn không thể thay đổi");
		}
		
		OrderStatus theStatus = null;
		try {
			theStatus = OrderStatus.valueOf(request.getStatus());
		} catch (IllegalArgumentException e) {
			throw new IllegalAccessException("Status invalid");
		}
		
		if(theStatus.name().equals("CANCELLED")) {
			throw new IllegalAccessException("Bạn không thể hủy đơn hàng của người dùng");
		}

		theOrder.setStatus(theStatus);

		// when order sent
		if (theStatus.name().equals("SENT")) {
			// add the sent time
			theOrder.setSentAt(LocalDateTime.now());

			// update sold number
			List<OrderItem> orderItems = theOrder.getItems();
			orderItems.forEach(orderItem -> {
				productVariationService.updateSoldNumber(orderItem.getProductItem().getId(), orderItem.getQuantity());
			});

		}

		return repository.save(theOrder);
	}
	
	@Override
	public PaginationResponse getByStatus(String status, int pageNumber) throws IllegalAccessException {

		OrderStatus theStatus = null;
		try {
			theStatus = OrderStatus.valueOf(status);
		} catch (IllegalArgumentException e) {
			 throw new IllegalAccessException("Status invalid");
		}
		
		Pageable paging = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "createdAt"));

		Page<Order> pageTuts = repository.findByStatus(theStatus, paging);
		
		List<OrderDTO> result = pageTuts.getContent().stream().map(o -> mapToDTO(o)).toList();

		return PaginationResponse.builder()
				.currentPage(pageTuts.getNumber())
				.totalItems(pageTuts.getTotalElements())
				.data(result)
				.totalPages(pageTuts.getTotalPages())
				.build();

	}

	@Override
	public Order cancelByAdmin(Order request) {

		Order theOrder = repository.findById(request.getId())
				.orElseThrow(() -> new ResourceNotFoundException("The order is not found"));

		theOrder.setStatus(OrderStatus.valueOf("CANCELLED"));

		return repository.save(theOrder);
	}

	@Override
	public Order cancelByUser(Order request) {

		User currentUser = userService.getCurrentUser();

		Order theOrder = repository.findByIdAndUserId(request.getId(), currentUser.getId())
				.orElseThrow(() -> new ResourceNotFoundException("The order is not found"));

		theOrder.setStatus(OrderStatus.valueOf("CANCELLED"));

		return repository.save(theOrder);
	}

	@Override
	public Order update(UpdateOrderDTO request) {

		User currentUser = userService.getCurrentUser();

		Order theOrder = repository.findByIdAndUserId(request.getOrderId(), currentUser.getId())
				.orElseThrow(() -> new ResourceNotFoundException("The order is not found"));

		Address address = addressRepository.findById(request.getAddressId())
				.orElseThrow(() -> new ResourceNotFoundException("The address is not found"));

		ShippingMethod shippingMethod = shippingMethodRepository.findById(request.getShippingId())
				.orElseThrow(() -> new ResourceNotFoundException("The shipping method is not found"));

		PaymentType paymentType = paymentRepository.findById(request.getPaymentId())
				.orElseThrow(() -> new ResourceNotFoundException("The payment type is not found"));

		theOrder.setNote(request.getNote());
		theOrder.setPayment(paymentType);
		theOrder.setShippingMethod(shippingMethod);
		theOrder.setAddress(address);

		return repository.save(theOrder);
	}

	@Override
	public Order getById(int id) {
		
		
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The order is not found"));
	}

	@Override
	public List<StatsDTO> getMonthlyRevenue(int year) {
        List<Object[]> results = repository.calculateMonthlyRevenue(year);
        
        List<StatsDTO> response = new ArrayList<>();
        

        Map<String, Double> monthlyRevenueMap = new LinkedHashMap<>();
        for (Month month : Month.values()) {
        	monthlyRevenueMap.put(month.name(), Double.parseDouble("0"));
        }
        for (Object[] result : results) {
            int monthNumber = (int) result[0];
            String month = Month.of(monthNumber).name();
            Double revenue = (Double) result[1];
            
            monthlyRevenueMap.put(month, revenue);
        }
        
        for (Map.Entry<String, Double> entry : monthlyRevenueMap.entrySet()) {
            String month = entry.getKey();
            Double revenue = entry.getValue();
           
            response.add(StatsDTO.builder().name(month.substring(0, 3)).total(revenue).build());
        }

        return response;
    }

	@Override
	public List<Order> getTop5() {
		
		return repository.findFirst5ByOrderByCreatedAtDesc();
	}

	@Override
	public List<OrderItem> getReviewableOrderItems() {
		
		User user = userService.getCurrentUser();
		LocalDateTime now = LocalDateTime.now();
		
		LocalDateTime startExpiredDate =  now.minusDays(7);
		
		return repository.findReviewableProductsByUserId(user.getId(), startExpiredDate);
	}

	
}
