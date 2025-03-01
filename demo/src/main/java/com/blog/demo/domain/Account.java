package com.blog.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Account {
    @Id
    private Long memberId;

    private String id;

    private String password;

    @OneToOne(mappedBy = "account")
    private Member member;

    public Account(Long memberId, String id, String password) {
        this.memberId = memberId;
        this.id = id;
        this.password = password;
    }
}
