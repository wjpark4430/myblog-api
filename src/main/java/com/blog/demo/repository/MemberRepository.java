package com.blog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.demo.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
