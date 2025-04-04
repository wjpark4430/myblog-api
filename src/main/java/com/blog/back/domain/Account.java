package com.blog.back.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Account {
    @Id
    private Long memberId;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne
    @MapsId
    private Member member;

    private Account(String userId, String password, Member member) {
        this.userId = userId;
        this.password = password;
        this.member = member;
    }

    public static Account ofCreate(String userId, String password, Member member) {
        return new Account(userId, password, member);
    }

    public void update(String password) {
        this.password = password;
    }
}
