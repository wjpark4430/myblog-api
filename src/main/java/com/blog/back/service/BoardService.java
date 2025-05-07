package com.blog.back.service;

import org.springframework.data.domain.Pageable;

import com.blog.back.dto.board.BoardCreateRequestDTO;
import com.blog.back.dto.board.BoardListResponseDTO;
import com.blog.back.dto.board.BoardResponseDTO;
import com.blog.back.dto.board.BoardUpdateRequestDTO;

public interface BoardService {
    BoardListResponseDTO getBoards(Pageable pageable);

    BoardResponseDTO getBoard(Long id);

    Long createBoard(BoardCreateRequestDTO dto);

    void updateBoard(Long id, BoardUpdateRequestDTO dto);

    void deleteBoard(Long id);
}
