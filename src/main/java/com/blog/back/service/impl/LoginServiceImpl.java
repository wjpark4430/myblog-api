package com.blog.back.service.impl;

import org.springframework.stereotype.Service;

import com.blog.back.domain.Account;
import com.blog.back.repository.AccountRepository;
import com.blog.back.service.LoginService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AccountRepository accountRepository;

    @Override
    public Long login(String userId, String password) {
        Account loginAccount = accountRepository.findByUserId(userId)
        .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(!loginAccount.getPassword().equals(password)){
            throw new RuntimeException("Incorrect Password");
        }

        return loginAccount.getMemberId();
    }
}