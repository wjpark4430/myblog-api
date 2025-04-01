package com.blog.back.service.impl;

import org.springframework.stereotype.Service;

import com.blog.back.domain.Member;
import com.blog.back.dto.member.MemberRegisterRequestDTO;
import com.blog.back.jwt.JwtService;
import com.blog.back.repository.AccountRepository;
import com.blog.back.repository.MemberRepository;
import com.blog.back.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final JwtService jwtService;

    // 회원 가입
    @Override
    public Long registerMember(MemberRegisterRequestDTO dto) {
        Member member = Member.ofCreate(dto.getName(), dto.getEmail());

        Member createMember = memberRepository.save(member);

        Long id = createMember.getId();

        return id;
    }
    // 회원 수정

    // 회원 탈퇴?
    @Override
    public void deleteMember(HttpServletRequest request, HttpServletResponse response) {

        String username = jwtService.getUserNameByRequest(request);

        Long memberId = accountRepository.findMemberIdByUserId(username);

        memberRepository.deleteById(memberId);

        jwtService.removeTokenCookie(response);

    }
}
