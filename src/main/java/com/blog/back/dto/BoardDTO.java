package com.blog.back.dto;

import lombok.Getter;

@Getter
public class BoardDTO {
    private String title;
    private String content;

    public BoardDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
