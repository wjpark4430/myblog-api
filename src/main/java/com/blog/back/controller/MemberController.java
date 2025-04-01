package com.blog.back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.back.dto.member.MemberRegisterRequestDTO;
import com.blog.back.repository.AccountRepository;
import com.blog.back.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AccountRepository accountRepository;

    @PostMapping
    public ResponseEntity<Long> register(@RequestBody MemberRegisterRequestDTO dto) {
        Long memberId = memberService.registerMember(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberId);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(HttpServletRequest request, HttpServletResponse response) {

        memberService.deleteMember(request, response);

        return ResponseEntity.noContent().build();
    }

}
