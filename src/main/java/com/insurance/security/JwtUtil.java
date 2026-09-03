package com.insurance.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getSigningKey() {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("JWT secret key cannot be null or empty. Check application.properties: jwt.secret");
        }
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public boolean isTokenExpired(LocalDateTime tokenEndTime){

        return tokenEndTime.isBefore(LocalDateTime.now());
    }

    public String generateToken(String email){

        Key key = getSigningKey();

        Date now = new Date();

        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token){

        if(token == null || token.isBlank()){

            throw new IllegalArgumentException(
                    "JWT token is null or empty");
        }

        Key key = getSigningKey();

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token){

        try{

            Key key = getSigningKey();

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;

        }catch (Exception e){

            return false;
        }
    }
}
