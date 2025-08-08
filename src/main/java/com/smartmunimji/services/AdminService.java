package com.smartmunimji.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartmunimji.daos.AdminDao;
import com.smartmunimji.entities.Admin;

import java.util.Optional;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Admin registerAdmin(Admin admin) {
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		return adminDao.save(admin);
	}

	public Optional<Admin> getByEmail(String email) {
		return adminDao.findByEmail(email);
	}
}
