package com.blog.back.jwt;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public String generateAccessToken(String sub) {
        return jwtTokenProvider.createAccessToken(sub, List.of("ROLE_USER"));
    }

    public String generateRefreshToken(String sub) {
        return jwtTokenProvider.createRefreshToken(sub);
    }

    public void setTokenCookies(HttpServletResponse response, String sub, String accessToken, String refreshToken) {
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60)
                .build();

        // response.setHeader("Authorization", "Bearer " + accessToken);
        redisTemplate.opsForValue().set("refresh:" + sub, refreshToken, 7, TimeUnit.DAYS);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
    }

    public void removeTokenCookie(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        if (refreshToken != null && validateToken(refreshToken)) {
            String userId = jwtTokenProvider.getUsernameFromToken(refreshToken);
            redisTemplate.delete("refresh:" + userId);
        }

        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());

    }

    public String getUserNameByRequest(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);
        String username = jwtTokenProvider.getUsernameFromToken(token);

        return username;
    }

    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    public void reissueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String redisRefreshToken = redisTemplate.opsForValue().get("refresh:" + jwtTokenProvider.getUsernameFromToken(refreshToken));

        if (!refreshToken.equals(redisRefreshToken) || refreshToken == null || !validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String userId = jwtTokenProvider.getUsernameFromToken(refreshToken);
        String newAccessToken = generateAccessToken(userId);

        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", newAccessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60)
                .build();

        response.addHeader("Set-Cookie", accessTokenCookie.toString());
    }
}
