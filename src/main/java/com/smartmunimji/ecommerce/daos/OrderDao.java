package com.smartmunimji.ecommerce.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartmunimji.ecommerce.entities.Order;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {

	Optional<Order> findByOrderIdAndDateOfPurchase(Integer orderId, LocalDate dateOfPurchase);
	
	Order findByProductId(Integer productId);
}
