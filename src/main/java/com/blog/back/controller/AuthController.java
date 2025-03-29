package com.blog.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.back.dto.member.MemberLoginRequestDto;
import com.blog.back.service.LoginService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final LoginService loginService;
    
    @PostMapping("/login")
    public ResponseEntity<Long> checkAuth(
        @RequestBody MemberLoginRequestDto loginRequestDto,
     HttpServletResponse response) {
        Long memberId = loginService.login(loginRequestDto.getUserId(), loginRequestDto.getPassword());
        
        Cookie cookie = new Cookie("memberId", memberId.toString());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
        response.addCookie(cookie);

        return ResponseEntity.ok().body(memberId);
    }
}
