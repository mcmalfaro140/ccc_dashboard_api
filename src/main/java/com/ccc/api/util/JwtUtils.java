package com.ccc.api.util;

import java.util.Calendar;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import com.ccc.api.model.User;

@Component
public class JwtUtils {
	private String issuer = "ccc";
	private SecretKey secretKey;
	
	public JwtUtils(@Value("${jwt.secret}") String secret) {
		byte[] keyBytes = Decoders.BASE64URL.decode(secret);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
	}

	public String toToken(User user) {
		Calendar expiration = Calendar.getInstance();
		expiration.add(Calendar.DATE, 7);
		
		Claims userClaims = Jwts.claims();
		userClaims.put("UserId", user.getUserId());
		userClaims.put("Username", user.getUsername());
		userClaims.put("Email", user.getEmail());  
		    
		return Jwts.builder().setIssuer(issuer).setSubject(user.getUserId().toString())
				.setExpiration(expiration.getTime()).setClaims(userClaims).signWith(this.secretKey).compact();
	}

	public User toUser(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody();
			
			User user = new User();
			user.setUserId(claims.get("UserId", Long.class));
			user.setUsername(claims.get("Username", String.class));
			user.setEmail(claims.get("Email", String.class));
			
			return user;
		} catch (JwtException e) {
		      return null;
		}
	}
}