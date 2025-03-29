package com.blog.back.dto.board;

import lombok.Getter;

@Getter
public class BoardCreateRequestDTO {
    private String title;
    private String content;
    private String image;
}
