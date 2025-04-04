package com.blog.back.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.back.domain.Account;
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
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    @Override
    @Transactional
    public Long registerMember(MemberRegisterRequestDTO dto) {
        Member member = Member.ofCreate(dto.getUsername(), dto.getEmail());

        Member createMember = memberRepository.save(member);

        Long id = createMember.getId();
        String username = dto.getUserId();
        String password = passwordEncoder.encode(dto.getPassword());

        Account account = Account.ofCreate(username, password, createMember);

        accountRepository.save(account);

        return id;
    }
    // 회원 수정

    // 회원 탈퇴?
    @Override
    @Transactional
    public void deleteMember(HttpServletRequest request, HttpServletResponse response) {

        String username = jwtService.getUserNameByRequest(request);

        Long memberId = accountRepository.findMemberIdByUserId(username);

        memberRepository.deleteById(memberId);

        jwtService.removeTokenCookie(response);

    }
}
