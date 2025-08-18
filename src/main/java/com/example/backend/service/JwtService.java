package com.example.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.*;

@Service
public class JwtService {

    @Value("${app.jwtSecret}")
    private String secretKey;
    @Value("${app.jwtExpirationMs}")
    private long exp;

    private SecretKey getKey(){
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public String generateToken(UserDetails userDetails) {
        Date today = new Date();
        Date expDate = new Date(today.getTime() + exp);

        return Jwts.builder()
                .subject(String.valueOf(userDetails.getUsername()))
                .issuedAt(today)
                .expiration(expDate)
                .signWith(getKey(), SIG.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
                final Claims claims = extractAllClaims(token);
                return resolver.apply(claims);
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token){
        try{
            extractAllClaims(token);
            return true;
        }catch (Exception e){
            throw new RuntimeException(" Jwt validation error", e);
        }

    }



}
