package com.blog.back.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    private final long validityInMilliseconds = 60 * 60 * 1000; // 1 hour
    private final long refreshTokenValidityInMilliseconds = 24 * 60 * 60 * 1000 * 7; // 7 days

    public String createAccessToken(String sub, List<String> roles) {
        return createToken(sub, roles, validityInMilliseconds);
    }

    public String createRefreshToken(String sub) {
        return createToken(sub, null, refreshTokenValidityInMilliseconds);
    }

    public String reissueAccessToken(String refreshToken) {
        if (!validateToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String username = getUsernameFromToken(refreshToken);
        List<String> roles = getRolesFromRefreshToken(refreshToken);

        return createAccessToken(username, roles);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.warn("Token has expired!");
        } catch (MalformedJwtException e) {
            log.warn("Token format is invalid!");
        } catch (SignatureException e) {
            log.warn("Token signature is invalid!");
        } catch (Exception e) {
            log.warn("Token validation failed!");
        }
        return false;
    }

    public String resolveAccessToken(HttpServletRequest request) {
        // 1. Authorization 헤더에서 찾기
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        // 2. Cookie에서 accessToken 찾기
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> "accessToken".equals(cookie.getName())) // accessToken 쿠키 찾기
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> "refreshToken".equals(cookie.getName())) // refreshToken 쿠키 찾기
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private List<String> getRolesFromRefreshToken(String token) {
        // 실제로는 Redis 등에서 꺼내거나 DB에서 사용자 권한 조회
        return List.of("ROLE_USER");
    }

    private String createToken(String username, List<String> roles, long validity) {
        Claims claims = Jwts.claims().setSubject(username);
        if (roles != null) {
            claims.put("roles", roles);
        }

        Date now = new Date();
        Date expiration = new Date(now.getTime() + validity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
