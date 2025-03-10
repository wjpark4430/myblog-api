package com.blog.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Account {
    @Id
    private Long memberId;

    private String userId;

    private String password;

    @OneToOne
    @MapsId
    private Member member;

    public Account(String userId, String password, Member member) {
        this.userId = userId;
        this.password = password;
        this.member = member;
    }
}
