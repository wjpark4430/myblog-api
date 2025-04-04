package com.blog.back.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequestDTO {
    private String username;
    private String email;
    private String userId;
    private String password;
}
