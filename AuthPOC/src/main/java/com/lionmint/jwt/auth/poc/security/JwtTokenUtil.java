package com.lionmint.jwt.auth.poc.security;

import static com.lionmint.jwt.auth.poc.entities.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.lionmint.jwt.auth.poc.entities.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	@Value(value = "${jwt.constants.signing_key}")
	public String signingKey;
	
	private final String user = "http://lionmint.com";
	
	public String getUsernameFromToken(String token) {
	    return getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) {
	    return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	    final Claims claims = getAllClaimsFromToken(token);
	    return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
	    return Jwts.parser()
	            .setSigningKey(signingKey)
	            .parseClaimsJws(token)
	            .getBody();
	}
	
	private Boolean isTokenExpired(String token) {
	    final Date expiration = getExpirationDateFromToken(token);
	    return expiration.before(new Date());
	}
	
	public String generateToken(UserEntity user) {
	    return doGenerateToken(user.getEmail());
	}
	
	private String doGenerateToken(String subject) {
	
	    final Claims claims = Jwts.claims().setSubject(subject);
	    claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
	
	    return Jwts.builder()
	            .setClaims(claims)
	            .setIssuer(user)
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS))
	            .signWith(SignatureAlgorithm.HS256, signingKey)
	            .compact();
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
	    final String username = getUsernameFromToken(token);
	    return (
	          username.equals(userDetails.getUsername())
	                && !isTokenExpired(token));
	}
}
