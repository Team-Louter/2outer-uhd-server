package com.louter.uhd.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.Collections;

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

    public Authentication getAuthentication(String token) {
        String userEmail = getUserEmailFromToken(token);

        return new UsernamePasswordAuthenticationToken(
                userEmail,
                null,
                Collections.emptyList()
        );
    }
}
