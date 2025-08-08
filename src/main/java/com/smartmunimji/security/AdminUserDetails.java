package com.smartmunimji.security;

import com.smartmunimji.entities.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AdminUserDetails implements UserDetails {

	private final Admin admin;

	public AdminUserDetails(Admin admin) {
		this.admin = admin;
	}

	public Integer getId() {
		return admin.getId(); // if id is Integer
	}

	public String getRole() {
		return "ROLE_ADMIN";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(getRole()));
	}

	@Override
	public String getPassword() {
		return admin.getPassword();
	}

	@Override
	public String getUsername() {
		return admin.getEmail(); // or admin.getUsername()
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true; // or admin.isEnabled() if present
	}

	public Admin getAdmin() {
		return admin;
	}
}
