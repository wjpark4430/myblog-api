package com.blog.back.service;

import com.blog.back.dto.member.MemberRegisterRequestDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {

    Long registerMember(MemberRegisterRequestDTO dto);

    void deleteMember(HttpServletRequest request, HttpServletResponse response);
}