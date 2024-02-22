package com.danglich.bantra.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.danglich.bantra.config.AppProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
	
	private final AppProperties appProperties;
	
	
	private String createToken(Map<String, Object> claims , String subject) {
		Date now = new Date();
		
		Date expirationDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
		
		return Jwts.builder()
					.setClaims(claims)
					.setExpiration(expirationDate)
					.setSubject(subject)
					.setIssuedAt(now)
					.signWith(getSignInKey(), SignatureAlgorithm.HS256)
					.compact();
	}
	
	private Key getSignInKey() {
	    byte[] keyBytes = Decoders.BASE64.decode(appProperties.getAuth().getTokenSecret());
	    return Keys.hmacShaKeyFor(keyBytes);
	  }
	
	public String generateToken(UserDetails userDetails) {
		
		Map<String , Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
		
	}
	
	private Claims extractAllClaims(String token) {
		
		return Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token).getBody();
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		
		Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
		
	}
	
	public String extractUsername(String token ) {
		
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		
		return extractClaim(token, Claims::getExpiration);
	}
	
	private boolean isTokenExpired(String token) {
		Date expirationDate = extractExpiration(token);
		
		return expirationDate.before(new Date());
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

}
