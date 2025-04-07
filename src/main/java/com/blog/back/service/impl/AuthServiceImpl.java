package com.blog.back.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.blog.back.dto.member.MemberLoginRequestDto;
import com.blog.back.jwt.JwtService;
import com.blog.back.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void login(MemberLoginRequestDto dto, HttpServletResponse response) {
        String userId = dto.getUserId();
        String password = dto.getPassword();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, password);
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            throw e;
        }
        
        String accessToken = jwtService.generateAccessToken(authentication.getName());
        String refreshToken = jwtService.generateRefreshToken(authentication.getName());

        jwtService.setTokenCookies(response, userId, accessToken, refreshToken);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        jwtService.removeTokenCookie(request, response);
    }

    @Override
    public boolean isLogin(String token) {
        return token != null && !token.isEmpty() && jwtService.validateToken(token);
    }

}