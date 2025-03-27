package com.blog.back.dto;

import lombok.Getter;

@Getter
public class BoardUpdateRequestDTO {
    private String title;
    private String content;
    private String image;
}
