package com.example.project.Security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.project.Model.Utilisateur;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

 @Value("${jwt.secret}")
 private String secretKey;

 @Value("${jwt.expiration}")
 private long expiration;

 public String generateToken(UserDetails userDetails) {
	 
	 Utilisateur user = (Utilisateur) userDetails;
	 
	 Map<String, Object> claims = new HashMap<>();

	    claims.put("id", user.getId());
	    claims.put("name", user.getNom());
	    claims.put("email", user.getEmail());
	    claims.put("role", user.getRole());
	 
     return Jwts.builder()
    		 .setClaims(claims)
             .setSubject(userDetails.getUsername())
             .setIssuedAt(new Date())
             .setExpiration(new Date(System.currentTimeMillis() + expiration))
             .signWith(getSignKey(), SignatureAlgorithm.HS256)
             .compact();
 }

 public String extractUsername(String token) {
     return getClaims(token).getSubject();
 }

 public boolean isTokenValid(String token, UserDetails userDetails) {
     return extractUsername(token).equals(userDetails.getUsername())
             && !getClaims(token).getExpiration().before(new Date());
 }

 private Claims getClaims(String token) {
     return Jwts.parserBuilder()
             .setSigningKey(getSignKey())
             .build()
             .parseClaimsJws(token)
             .getBody();
 }

 private Key getSignKey() {
     return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
 }
}