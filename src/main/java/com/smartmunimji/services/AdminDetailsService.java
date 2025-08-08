package com.smartmunimji.services;

import com.smartmunimji.daos.AdminDao;
import com.smartmunimji.entities.Admin;
import com.smartmunimji.security.AdminUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsService implements UserDetailsService {

	@Autowired
	private AdminDao adminDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Admin admin = adminDao.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Admin not found with email: " + email));

		return new AdminUserDetails(admin);
	}
}
