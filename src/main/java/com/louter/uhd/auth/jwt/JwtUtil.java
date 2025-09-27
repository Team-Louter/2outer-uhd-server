package com.louter.uhd.auth.jwt;

import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    protected final Key key;
    protected final Long expiration;

    public JwtUtil(String secretKey, Long expiration) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expiration = expiration;
    }
}
