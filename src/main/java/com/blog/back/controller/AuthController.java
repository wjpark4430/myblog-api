package com.blog.back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.back.dto.member.MemberLoginRequestDto;
import com.blog.back.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/check")
    public ResponseEntity<Void> checkUser(@CookieValue(name = "accessToken", required = false) String token) {
        if (!authService.isLogin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginRequest(
            @RequestBody MemberLoginRequestDto loginRequestDto,
            HttpServletResponse response) {
        authService.login(loginRequestDto, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutRequest(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/refresh")
    public ResponseEntity<Void> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {
        // if (refreshToken == null || !jwtService.validateToken(refreshToken)) {
        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        // }
        return ResponseEntity.ok().build();
    }

}
