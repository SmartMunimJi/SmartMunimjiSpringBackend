package com.smartmunimji.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartmunimji.entities.Customer;
import com.smartmunimji.entities.RegisteredProduct;

import java.util.Optional;
import java.util.List;

@Repository
public interface RegisteredProductDao extends JpaRepository<RegisteredProduct, Integer> {

	Optional<RegisteredProduct> findByOrderIdAndCustomerId(Integer orderId, Integer customerId);

	List<RegisteredProduct> findByCustomerId(Integer customerId);

	List<RegisteredProduct> findBySellerId(Integer sellerId);

//    List<RegisteredProduct> findByStatus(RegisteredProduct.Status status);

	boolean existsByOrderId(Integer orderId);
	
	Optional<RegisteredProduct> findByOrderIdAndProductId(Integer orderId, int productId);
	
	boolean existsByCustomerAndOrderId(Customer customer, Integer orderId);

}
