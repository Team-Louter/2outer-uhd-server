package com.louter.uhd.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenProvider extends JwtUtil {

    public JwtTokenProvider(String secretKey, Long expiration) {
        super(secretKey, expiration);
    }

    // 토큰 생성
    public String generateToken(String userEmail) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
