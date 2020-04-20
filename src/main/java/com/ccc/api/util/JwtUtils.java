package com.ccc.api.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ccc.api.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	private static final String ISSUER = "ccc";
	private SecretKey secretKey;
	
	public JwtUtils(@Value("${jwt.secret}") String secret) {
		byte[] keyBytes = Decoders.BASE64URL.decode(secret);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
	}

	public String toToken(User user) {
		Date expirationDate = this.computeExpirationDate();
		
		Claims userClaims = Jwts.claims();
		userClaims.put("UserId", user.getUserId());
		userClaims.put("Username", user.getUsername());
		    
		return Jwts.builder().setIssuer(JwtUtils.ISSUER).setSubject(user.getUserId().toString())
				.setExpiration(expirationDate).setClaims(userClaims).signWith(this.secretKey).compact();
	}
	
	private Date computeExpirationDate() {
		final long maxDays = 30;
		LocalDateTime expirationDate = LocalDateTime.now().plusDays(maxDays);
		
		return Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant());
	}

	public User toUser(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody();
			
			User user = new User();
			user.setUserId(claims.get("UserId", Long.class));
			user.setUsername(claims.get("Username", String.class));
			
			return user;
		} catch (JwtException e) {
		    return null;
		}
	}
}