//package com.smartmunimji.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.smartmunimji.daos.AdminDao;
//import com.smartmunimji.daos.CustomerDao;
//import com.smartmunimji.daos.SellerDao;
//import com.smartmunimji.entities.Admin;
//import com.smartmunimji.entities.Customer;
//import com.smartmunimji.entities.Seller;
//
//@Service
//public class UnifiedUserDetailsService implements UserDetailsService {
//    @Autowired
//    private AdminDao adminRepository;
//    @Autowired
//    private CustomerDao customerRepository;
//    @Autowired
//    private SellerDao sellerRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Admin admin = adminRepository.findByEmail(email);
//        if (admin != null) return admin;
//
//        Customer customer = customerRepository.findByEmail(email);
//        if (customer != null) return customer;
//
//        Seller seller = sellerRepository.findBySellersemail(email);
//        if (seller != null) return seller;
//
//        throw new UsernameNotFoundException("User not found with email: " + email);
//    }
//}

package com.smartmunimji.services;

import com.smartmunimji.daos.AdminDao;
import com.smartmunimji.daos.CustomerDao;
import com.smartmunimji.daos.SellerDao;
import com.smartmunimji.entities.Admin;
import com.smartmunimji.entities.Customer;
import com.smartmunimji.entities.Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UnifiedUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private SellerDao sellerDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Admin> adminOpt = adminDao.findByEmail(email);
		if (adminOpt.isPresent()) {
			Admin admin = adminOpt.get();
			return new User(admin.getEmail(), admin.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
		}

		Optional<Customer> customerOpt = customerDao.findByEmail(email);
		if (customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			return new User(customer.getEmail(), customer.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
		}

		Optional<Seller> sellerOpt = sellerDao.findBySellersemail(email);
		if (sellerOpt.isPresent()) {
			Seller seller = sellerOpt.get();
			return new User(seller.getSellersemail(), seller.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority("ROLE_SELLER")));
		}

		throw new UsernameNotFoundException("User not found with email: " + email);
	}
}
