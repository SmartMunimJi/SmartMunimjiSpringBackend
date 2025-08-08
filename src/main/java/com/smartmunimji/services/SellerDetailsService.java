package com.smartmunimji.services;

import com.smartmunimji.daos.SellerDao;
import com.smartmunimji.entities.Seller;
import com.smartmunimji.security.SellerUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class SellerDetailsService implements UserDetailsService {

	@Autowired
	private SellerDao sellerDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Seller seller = sellerDao.findBySellersemail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Seller not found with email: " + email));

		return new SellerUserDetails(seller);
	}
}
