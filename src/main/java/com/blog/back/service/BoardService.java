package com.blog.back.service;

import java.util.List;

import com.blog.back.dto.BoardDTO;

public interface BoardService {
    List<BoardDTO> getBoards();
}
