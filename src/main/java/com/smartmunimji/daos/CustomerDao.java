package com.smartmunimji.daos;

import com.smartmunimji.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {
	Optional<Customer> findByEmail(String email);
}
