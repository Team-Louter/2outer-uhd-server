package com.louter.uhd.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtAuthenticationFilter jwtAuth;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> PERMIT_ALL_PATHS = Arrays.asList(
            "/",
            "/auth/update/**",
            "/auth/email/**",
            "/auth/signup",
            "/auth/login",
            "/auth/find/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api-docs",
            "/favicon.ico"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // 허용 경로면 그냥 통과
        if (isPermitAllPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 나머지 경로는 JWT 필수
        String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();

            try {
                jwtAuth.validateToken(token);
                String userEmail = jwtAuth.userIdFromToken(token);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userEmail, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                sendErrorResponse(response, "Invalid JWT token", e.getMessage());
                return;
            }
        } else {
            sendErrorResponse(response, "Unauthorized", "JWT token is required");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPermitAllPath(String path) {
        return PERMIT_ALL_PATHS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private void sendErrorResponse(HttpServletResponse response, String error, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format("{\"error\": \"%s\", \"message\": \"%s\"}", error, message));
    }
}