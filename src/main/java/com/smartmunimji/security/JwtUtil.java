package com.smartmunimji.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

	@Value("${jwt.token.secret}")
	private String secret;

	@Value("${jwt.token.expiration.millis}")
	private Long expiration;

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String createToken(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		Map<String, Object> claims = new HashMap<>();

		if (userDetails instanceof CustomerUserDetails) {
			CustomerUserDetails customUser = (CustomerUserDetails) userDetails;
			claims.put("userId", customUser.getId());
			claims.put("role", customUser.getRole());
		} else if (userDetails instanceof SellerUserDetails) {
			SellerUserDetails seller = (SellerUserDetails) userDetails;
			claims.put("userId", seller.getId());
			claims.put("role", seller.getRole());
		} else if (userDetails instanceof AdminUserDetails) {
			AdminUserDetails admin = (AdminUserDetails) userDetails;
			claims.put("userId", admin.getId());
			claims.put("role", admin.getRole());
		}
		
		claims.put("roles", userDetails.getAuthorities());

		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)).signWith(getSigningKey())
				.compact();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
	    try {
	        if (token.contains(" ")) {
	            System.err.println("❌ Token contains spaces! Invalid format: [" + token + "]");
	        } else {
	            System.out.println("✅ Clean token: " + token);
	        }
	        return Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    } catch (Exception e) {
	        throw new RuntimeException("Invalid JWT: " + e.getMessage());
	    }
	}


	public int extractUserId(String token) {
		Object userId = extractAllClaims(token).get("userId");
		return userId instanceof Integer ? (Integer) userId : ((Number) userId).intValue();
	}

	public String extractRole(String token) {
		return (String) extractAllClaims(token).get("role");
	}

	public String extractUsernameFromToken(String token) {
		return extractClaim(token, Claims::getSubject); // Assumes 'sub' holds the email/username
	}

}
