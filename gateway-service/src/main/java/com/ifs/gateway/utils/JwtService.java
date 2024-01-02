package com.ifs.gateway.utils;

import java.security.Key;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	 private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
	
	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	public void validateToken(String token) {
		try {
			
            Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
            Claims tokenClaims = claims.getBody();
            logger.info("Token validation successful. Token claims: {}", tokenClaims);
            
        } catch (ExpiredJwtException ex) {
            logger.error("Token has expired: {}", ex.getMessage());
            throw new RuntimeException("Token has expired");
        } catch (JwtException ex) {
            logger.error("Token validation failed: {}", ex.getMessage());
            throw new RuntimeException("Token validation failed");
        }
	}

	public String getRole(String token) {
		Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token).getBody();
		return (String) claims.get("role");
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}
	
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
