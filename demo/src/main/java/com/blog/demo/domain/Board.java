package com.blog.demo.domain;

import java.time.ZonedDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Long like_count;

    private ZonedDateTime changed_at;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToOne(mappedBy = "board", cascade = CascadeType.ALL)
    private Like like;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardTag> boardTags;

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
        this.like_count = Long.valueOf(0);
        this.changed_at = ZonedDateTime.now();
    }

}
