package com.smartmunimji.services;

import com.smartmunimji.daos.CustomerDao;
import com.smartmunimji.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Customer registerCustomer(Customer customer) {
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		return customerDao.save(customer);
	}

	public Optional<Customer> getByEmail(String email) {
		return customerDao.findByEmail(email);
	}

	public boolean emailExists(String email) {
		return customerDao.findByEmail(email).isPresent();
	}
}
