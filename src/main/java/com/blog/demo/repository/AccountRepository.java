package com.blog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.demo.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
