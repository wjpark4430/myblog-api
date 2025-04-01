package com.blog.back.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<BoardLike> likes;

    private Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static Member ofCreate(String name, String email) {
        return new Member(name, email);
    }

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
