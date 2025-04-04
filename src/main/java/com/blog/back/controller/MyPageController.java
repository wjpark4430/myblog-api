package com.blog.back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    @GetMapping("/{userId}")
    public ResponseEntity<?> getMyPage(@PathVariable String userId,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (!userId.equals(userDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: User ID mismatch");
        }
        // 마이페이지 정보 반환
        return ResponseEntity.ok("My Page Information for user: " + userId);
    }
}
