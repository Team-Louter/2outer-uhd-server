package com.louter.uhd.auth.jwt;

import com.louter.uhd.auth.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtAuthenticationFilter extends JwtUtil {

    public JwtAuthenticationFilter(String secretKey, Long expiration) {
        super(secretKey, expiration);
    }

    // 아이디로 토큰 생성
    public String userIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // 토큰 만료 확인, 해당 클래스에서 자체적으로 호출해서, 추가적인 호출 필요 없음
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException(e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}