package com.blog.demo.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Like> likes;

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
