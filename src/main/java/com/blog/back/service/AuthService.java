package com.blog.back.service;

import com.blog.back.dto.member.MemberLoginRequestDto;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void login(MemberLoginRequestDto dto, HttpServletResponse response);

    void logout(HttpServletResponse response);

    boolean isLogin(String token);
}