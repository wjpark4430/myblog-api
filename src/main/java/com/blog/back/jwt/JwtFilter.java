package com.blog.back.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        log.info("Request Method: {}", request.getMethod());
        log.info("Request URL: {}", request.getRequestURL());

        String accessToken = jwtTokenProvider.resolveAccessToken(request);

        if (accessToken != null) {
            if (jwtTokenProvider.validateToken(accessToken)) {
                // access token 유효 → 인증 처리
                log.info("Access token is valid.");
                authenticate(accessToken);
            } else {
                // access token 만료 → refresh token 확인
                String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

                if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
                    String newAccessToken = jwtTokenProvider.reissueAccessToken(refreshToken);
                    jwtService.reissueToken(request, response);
                    log.info(accessToken + " is expired, but refresh token is valid. New access token issued.");
                    authenticate(newAccessToken);
                } else {
                    // refresh token도 만료 → 로그인 필요
                    log.info("Access token and refresh token are both invalid.");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    private void authenticate(String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
