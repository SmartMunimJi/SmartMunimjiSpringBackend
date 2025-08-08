package com.smartmunimji.controllers;

import com.smartmunimji.dtos.Credentials;
import com.smartmunimji.entities.Seller;
import com.smartmunimji.security.JwtUtil;
import com.smartmunimji.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sm/seller/auth")
@CrossOrigin(origins = "*")
public class SellerController {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Seller seller) {
		Seller saved = sellerService.registerSeller(seller);
		return ResponseEntity.ok("Seller registered with ID: " + saved.getId());
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Credentials credentials) {
		try {
			Authentication auth = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
			String token = jwtUtil.createToken(auth);
			return ResponseEntity.ok(Map.of("message", "Login successful", "token", token));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
		}
	}
}
