package com.blog.back.service;

import org.springframework.data.domain.Pageable;

import com.blog.back.dto.BoardCreateRequestDTO;
import com.blog.back.dto.BoardListResponseDTO;
import com.blog.back.dto.BoardResponseDTO;
import com.blog.back.dto.BoardUpdateRequestDTO;

public interface BoardService {
    BoardListResponseDTO getBoards(Pageable pageable);

    BoardResponseDTO getBoard(Long id);

    Long createBoard(BoardCreateRequestDTO dto);

    void updateBoard(Long id, BoardUpdateRequestDTO dto);
}
