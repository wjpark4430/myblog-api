package com.blog.back.domain;

import java.time.ZonedDateTime;
import java.util.List;

import com.blog.back.dto.board.BoardUpdateRequestDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private Long likeCount;

    @Column(updatable = false)
    private ZonedDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private BoardLike like;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardTag> boardTags;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardImage> boardImages;

    private Board(String title, String content){
        this.title = title;
        this.content = content;
        this.likeCount = 0L;
        this.createdAt = ZonedDateTime.now();
    }

    public static Board ofCreate(String title, String content) {
        return new Board(title, content);
    }

    public void update(BoardUpdateRequestDTO dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

}
