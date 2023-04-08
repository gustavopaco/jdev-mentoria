package com.pacoprojects.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtAuthorizationService jwtAuthorizationService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            jwtAuthorizationService.getAuthorization(request);
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            Map<String, Object> map = new HashMap<>();

            if (exception instanceof AuthorizationServiceException) {
                map.put("message", exception.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            } else if (exception instanceof ExpiredJwtException) {
                map.put("message", "ExpiredJwtException");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            } else if (exception instanceof MalformedJwtException) {
                map.put("message", "Sessão foi invalidada, por favor faça o login novamente.");
                response.setStatus(HttpStatus.FORBIDDEN.value());
            } else if (exception instanceof SignatureException){
                map.put("message", "Token inválido");
                response.setStatus(HttpStatus.FORBIDDEN.value());
            } else {
                map.put("message", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        }
    }
}
