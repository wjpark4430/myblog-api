package com.blog.back.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.blog.back.dto.member.MemberLoginRequestDto;
import com.blog.back.jwt.JwtService;
import com.blog.back.service.LoginService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void login(MemberLoginRequestDto dto, HttpServletResponse response) {
        String userId = dto.getUserId();
        String password = dto.getPassword();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, password);
        Authentication authentication = authenticationManager.authenticate(authToken);

        String accessToken = jwtService.generateAccessToken(authentication.getName());
        String refreshToken = jwtService.generateRefreshToken(authentication.getName());

        jwtService.setTokenCookies(response, accessToken, refreshToken);
    }

    

}