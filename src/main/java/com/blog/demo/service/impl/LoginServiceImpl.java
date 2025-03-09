package com.blog.demo.service.impl;

import org.springframework.stereotype.Service;

import com.blog.demo.domain.Account;
import com.blog.demo.repository.AccountRepository;
import com.blog.demo.service.LoginService;

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