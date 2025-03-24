package com.blog.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.back.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
