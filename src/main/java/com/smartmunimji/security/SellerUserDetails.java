package com.smartmunimji.security;

import com.smartmunimji.entities.Seller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class SellerUserDetails implements UserDetails {

	private final Seller seller;

	public SellerUserDetails(Seller seller) {
		this.seller = seller;
	}

	public Integer getId() {
		return seller.getId();
	}

	public String getRole() {
		return "ROLE_SELLER";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(getRole()));
	}

	@Override
	public String getPassword() {
		return seller.getPassword();
	}

	@Override
	public String getUsername() {
		return seller.getSellersemail(); 
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
		return true;
	}

	public Seller getSeller() {
		return seller;
	}
}
