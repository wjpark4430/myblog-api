package com.blog.back.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class BoardLike {
    @Id
    private Long boardId;

    @OneToOne
    @MapsId
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private BoardLike(Board board, Member member) {
        this.board = board;
        this.member = member;
    }

    public static BoardLike ofCreate(Board board, Member member) {
        return new BoardLike(board, member);
    }

}
