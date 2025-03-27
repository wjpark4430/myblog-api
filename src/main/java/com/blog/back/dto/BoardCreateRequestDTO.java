package com.blog.back.dto;

import lombok.Getter;

@Getter
public class BoardCreateRequestDTO {
    private String title;
    private String content;
    private String image;
}
