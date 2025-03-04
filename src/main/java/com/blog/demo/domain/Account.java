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

    private String id;

    private String password;

    @OneToOne
    @MapsId
    private Member member;

    public Account(Long memberId, String id, String password) {
        this.memberId = memberId;
        this.id = id;
        this.password = password;
    }
}
