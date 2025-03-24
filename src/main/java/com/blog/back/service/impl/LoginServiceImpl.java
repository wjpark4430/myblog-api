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

    
    public boolean isLogin(String userId, String password) {
        Account loginAccount = accountRepository.findByUserId(userId)
        .orElseThrow(() -> new RuntimeException("Can't find ID"));
        return loginAccount.getUserId().equals(userId) && loginAccount.getPassword().equals(password);
    }

    @Override
    public Long login(String userId, String password) {
        if(!isLogin(userId, password)){
            throw new RuntimeException("Incorrect Password");
        }
        return accountRepository.findMemberIdByUserId(userId);
    }

}