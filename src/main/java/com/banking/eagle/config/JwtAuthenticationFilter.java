package com.banking.eagle.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        return path.startsWith("/v1/auth") ||
                path.startsWith("/h2-console") ||
                path.startsWith("/swagger-ui") ||
                path.equals("/swagger-ui.html") ||
                path.equals("/swagger-ui/index.html") ||
                path.startsWith("/v3/api-docs") ||
                path.equals("/v3/api-docs/swagger-config");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String username = jwtProvider.extractUsername(token);

                List<GrantedAuthority> authorities = jwtProvider.extractAuthorities(token);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username, null,  authorities);

                //Where the user is authenticated
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (Exception e) {
                // Invalid token, log if needed
            }
        }

        filterChain.doFilter(request, response);

    }
}
