package com.example.backend.security.configs;

import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;

@Component
public class JwtConfig {

    public SecretKey getJwtKey(){
        Claims claims = Decoders.BASE64()
    }

}
