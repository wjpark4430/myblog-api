package com.blog.back.jwt;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.back.domain.Account;
import com.blog.back.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account loginAccount = accountRepository.findByUserId(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + userName));

        UserDetails user = new User(
                loginAccount.getUserId(),
                loginAccount.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_USER")));

        return user;
    }
}
