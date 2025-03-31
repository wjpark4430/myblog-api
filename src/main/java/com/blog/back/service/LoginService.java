package com.blog.back.service;

import com.blog.back.dto.member.MemberLoginRequestDto;

import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {
    Long login(MemberLoginRequestDto dto, HttpServletResponse response);
}