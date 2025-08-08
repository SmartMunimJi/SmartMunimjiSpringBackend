package com.smartmunimji.security;

import com.smartmunimji.exceptions.AccessDeniedHandlerImpl;
import com.smartmunimji.exceptions.JwtAuthenticationEntryPoint;
import com.smartmunimji.services.AdminDetailsService;
import com.smartmunimji.services.CustomerDetailsService;
import com.smartmunimji.services.SellerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomerDetailsService customerDetailsService;
	@Autowired
	private AdminDetailsService adminDetailsService;
	@Autowired
	private SellerDetailsService sellerDetailsService;
	@Autowired
	private JwtFilter jwtFilter;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private AccessDeniedHandlerImpl accessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider customerAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customerDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public DaoAuthenticationProvider adminAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(adminDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public DaoAuthenticationProvider sellerAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(sellerDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return new ProviderManager(Arrays.asList(customerAuthenticationProvider(), adminAuthenticationProvider(),
				sellerAuthenticationProvider()));
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint)
						.accessDeniedHandler(accessDeniedHandler))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/sm/customer/auth/login", "/sm/customer/auth/register",
								"/sm/admin/auth/login", "/sm/admin/auth/register", "/sm/seller/auth/login",
								"/sm/seller/auth/register")
						.permitAll()
						.requestMatchers("/sm/cust/**", "/sm/customer/register-product", "/customer/warranty/claim")
						.hasRole("CUSTOMER").requestMatchers("/sm/admin/**").hasRole("ADMIN")
						.requestMatchers("/sm/seller/**").hasRole("SELLER").anyRequest().authenticated())
				.authenticationProvider(customerAuthenticationProvider())
				.authenticationProvider(adminAuthenticationProvider())
				.authenticationProvider(sellerAuthenticationProvider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
