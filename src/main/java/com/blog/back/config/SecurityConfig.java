package com.blog.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blog.back.jwt.CustomLogoutHandler;
import com.blog.back.jwt.CustomUserDetailsService;
import com.blog.back.jwt.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final CustomUserDetailsService customUserDetailsService;
        private final CustomLogoutHandler customLogoutHandler;
        private final PasswordEncoder passwordEncoder;

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
                AuthenticationManagerBuilder authenticationManagerBuilder = http
                                .getSharedObject(AuthenticationManagerBuilder.class);
                authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                                .passwordEncoder(passwordEncoder);
                return authenticationManagerBuilder.build();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
                http
                        .csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests(auth -> auth
                                        .requestMatchers("/auth", "/auth/login", "/member/register").permitAll()
                                        .requestMatchers("/boards/**", "/images/**").permitAll()
                                        .requestMatchers("/admin/**").hasRole("ADMIN")
                                        // 임시
                                        .requestMatchers("/mypage/**").permitAll()
                                        .anyRequest().authenticated())
                        .logout(logout -> logout
                                        .logoutUrl("/logout")
                                        .addLogoutHandler(customLogoutHandler)
                                        .logoutSuccessHandler((request, response, authentication) -> {
                                                response.sendRedirect("/");
                                        }))

                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}