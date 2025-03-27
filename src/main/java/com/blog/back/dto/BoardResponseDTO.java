package com.blog.back.dto;

import com.blog.back.domain.Board;
import com.blog.back.util.TimeUtils;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardResponseDTO {
    private final String title;
    private final String content;
    private final Long likeCount;
    private final String createdAt;

    @Builder
    private BoardResponseDTO(String title, String content, Long likeCount, String createdAt) {
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }

    public static BoardResponseDTO fromEntity(Board board) {
        return BoardResponseDTO.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .likeCount(board.getLikeCount())
                .createdAt(TimeUtils.formatCreatedAt(board.getCreatedAt()))
                .build();
    }
}
