package com.smartmunimji.controllers;

import com.smartmunimji.dtos.Credentials;
import com.smartmunimji.entities.Customer;
import com.smartmunimji.security.JwtUtil;
import com.smartmunimji.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sm/customer/auth")
@CrossOrigin(origins = "*")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authManager;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Customer customer) {
		Customer saved = customerService.registerCustomer(customer);
		return ResponseEntity.ok("Customer registered with ID: " + saved.getId());
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Credentials credentials) {
		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
			String token = jwtUtil.createToken(authentication);
			return ResponseEntity.ok(Map.of("message", "Login successful", "token", token));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
		}
	}
}
