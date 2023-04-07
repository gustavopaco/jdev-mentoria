package com.pacoprojects.security.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtilService {

    private final JwtConfig jwtConfig;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, new HashMap<>());
    }

    public String generateToken(UserDetails userDetails, Map<String,Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(Duration.ofDays(jwtConfig.getDaysUntilExpiration()))))
                .signWith(getSigningKey())
                .compact();
    }

    public Map<String, Object> breakToken(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String fullToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!Strings.isNullOrEmpty(fullToken)) {

            String basicToken = fullToken.replace(jwtConfig.getPrefix(), "");
            if (!isTokenExpired(basicToken)) {

                String username = extractUsername(basicToken);
                map.put("username", username);
                map.put("basicToken", basicToken);
                return map;
            }
        }
        return map;
    }

    private Claims extractAllClaims(String basicToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(basicToken)
                .getBody();
    }

    private <T> T extractClaim(String basicToken, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(basicToken);
        return claimsTFunction.apply(claims);
    }

    public String extractUsername(String basicToken) {
        return extractClaim(basicToken, Claims::getSubject);
    }

    private Date getExpirationDate(String basicToken) {
        return extractClaim(basicToken, Claims::getExpiration);
    }

    public boolean isTokenExpired(String basicToken) {
        Date expirationDate = getExpirationDate(basicToken);
        return new Date().after(expirationDate);
    }
}
