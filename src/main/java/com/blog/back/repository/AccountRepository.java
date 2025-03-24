package com.blog.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.back.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserId(String userId);

    Long findMemberIdByUserId(String userId);
    
}
