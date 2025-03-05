package com.blog.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
@Getter
public class Like {
    @Id
    private Long boardId;

    @OneToOne
    @MapsId
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Like(Board board, Member member) {
        this.board = board;
        this.member = member;
    }
}
