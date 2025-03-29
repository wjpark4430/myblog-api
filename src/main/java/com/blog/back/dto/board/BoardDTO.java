package com.blog.back.dto.board;

import java.time.ZonedDateTime;

import com.blog.back.domain.Board;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardDTO {

    private Long id;              
    private String title;          
    private String content;       
    private ZonedDateTime createdAt; 
    private long likeCount;        

    @Builder
    public BoardDTO(Long id, String title, String content, ZonedDateTime createdAt, long likeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
    }

    public static BoardDTO fromEntity(Board board) {
        return BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .likeCount(board.getLikeCount())
                .build();
    }
}
