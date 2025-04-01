package com.blog.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.back.dto.member.MemberLoginRequestDto;
import com.blog.back.service.LoginService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Void> checkAuth(
            @RequestBody MemberLoginRequestDto loginRequestDto,
            HttpServletResponse response) {
        loginService.login(loginRequestDto, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        
        return ResponseEntity.ok().build();
    }
    
}
