package com.smartmunimji.daos;

import com.smartmunimji.entities.WarrantyClaim;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WarrantyClaimDao extends JpaRepository<WarrantyClaim, Integer> {
	List<WarrantyClaim> findByCustomerId(int customerId);
}
