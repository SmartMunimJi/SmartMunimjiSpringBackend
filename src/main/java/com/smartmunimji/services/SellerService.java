package com.smartmunimji.services;

import com.smartmunimji.daos.SellerDao;
import com.smartmunimji.entities.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerService {

	@Autowired
	private SellerDao sellerDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Seller registerSeller(Seller seller) {
		seller.setPassword(passwordEncoder.encode(seller.getPassword()));
		return sellerDao.save(seller);
	}

	public Optional<Seller> getByEmail(String email) {
		return sellerDao.findBySellersemail(email);
	}

	public boolean emailExists(String email) {
		return sellerDao.findBySellersemail(email).isPresent();
	}
}
