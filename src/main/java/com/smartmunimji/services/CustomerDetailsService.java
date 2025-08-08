package com.smartmunimji.services;

import com.smartmunimji.daos.CustomerDao;
import com.smartmunimji.entities.Customer;
import com.smartmunimji.security.CustomerUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

	@Autowired
	private CustomerDao customerDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = customerDao.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Customer not found with email: " + email));

		return new CustomerUserDetails(customer);
	}
}
