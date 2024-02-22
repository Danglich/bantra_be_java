package com.danglich.bantra.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danglich.bantra.model.Order;
import com.danglich.bantra.model.OrderItem;
import com.danglich.bantra.model.OrderStatus;
import com.danglich.bantra.model.Product;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	List<Order> findByUserIdOrderByCreatedAtDesc(int userId);
	
	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.status = :status OR :status IS NULL) ORDER BY o.createdAt DESC")
	List<Order> findByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") OrderStatus status);
	
	Page<Order> findByStatus(OrderStatus status, Pageable page);

	Optional<Order> findByIdAndUserId(int id , int userId);
	
	List<Order> findFirst5ByOrderByCreatedAtDesc();
	
	@Modifying
    @Query("UPDATE Order o SET o.user = NULL WHERE o.user.id = :userId")
    void updateOrdersWithNullUser(@Param("userId") Integer userId);
	
	@Query("SELECT MONTH(o.createdAt) AS month, SUM(o.total) FROM Order o WHERE YEAR(o.createdAt) = :year GROUP BY month ORDER BY month")
    List<Object[]> calculateMonthlyRevenue(@Param("year") int year);
    
    
    // startDate là ngày thời gian hôm nay - 7 ngày
    @Query("SELECT oi FROM Order o JOIN o.items oi WHERE o.user.id = :userId AND o.status = 'SENT' AND oi.reviewed = FALSE AND o.sentAt > :startDate  ORDER BY o.sentAt DESC")
    List<OrderItem> findReviewableProductsByUserId(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate);
}
