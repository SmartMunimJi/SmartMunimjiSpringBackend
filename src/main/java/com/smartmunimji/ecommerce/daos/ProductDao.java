package com.smartmunimji.ecommerce.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartmunimji.ecommerce.entities.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
//	List<Product> findBySellerId(int sellerId);

	Optional<Product> findByName(String name);
	
	Optional<Product> findByNameIgnoreCase(String name);
}
