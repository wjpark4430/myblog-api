package com.blog.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class BoardLike {
    @Id
    private Long boardId;

    @OneToOne
    @MapsId
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public BoardLike(Board board, Member member) {
        this.board = board;
        this.member = member;
    }
}
