package com.smartmunimji.security;

import com.smartmunimji.services.AdminDetailsService;
import com.smartmunimji.services.CustomerDetailsService;
import com.smartmunimji.services.SellerDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomerDetailsService customerDetailsService;

	@Autowired
	private AdminDetailsService adminDetailsService;

	@Autowired
	private SellerDetailsService sellerDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");

		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			try {
				username = jwtUtil.extractUsername(token);
				System.out.println("JWT in Authorization header: [" + authHeader + "]");
                System.out.println("Extracted token: [" + token + "]");
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token" + e.getMessage());
				return;
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			String role = jwtUtil.extractRole(token);

			UserDetails userDetails = switch (role) {
			case "ROLE_CUSTOMER" -> customerDetailsService.loadUserByUsername(username);
			case "ROLE_SELLER" -> sellerDetailsService.loadUserByUsername(username);
			case "ROLE_ADMIN" -> adminDetailsService.loadUserByUsername(username);
			default -> null;
			};

			if (userDetails != null && jwtUtil.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}
