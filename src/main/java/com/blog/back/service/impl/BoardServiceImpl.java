package com.blog.back.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.back.domain.Board;
import com.blog.back.dto.board.BoardCreateRequestDTO;
import com.blog.back.dto.board.BoardListResponseDTO;
import com.blog.back.dto.board.BoardResponseDTO;
import com.blog.back.dto.board.BoardUpdateRequestDTO;
import com.blog.back.repository.BoardRepository;
import com.blog.back.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional(readOnly = true)
    public BoardListResponseDTO getBoards(Pageable pageable) {
        Long totalCount = boardRepository.count();
        List<Board> boards = boardRepository.findAll(pageable).getContent();

        return BoardListResponseDTO.fromBoards(boards, totalCount , pageable.getPageSize(), pageable.getPageNumber());
    }

    @Override
    @Transactional(readOnly = true)
    public BoardResponseDTO getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        return BoardResponseDTO.fromEntity(board);
    }

    @Override
    @Transactional
    public Long createBoard(BoardCreateRequestDTO dto) {
        Board board = Board.ofCreate(dto.getTitle(), dto.getContent());

        Board savedBoard = boardRepository.save(board);

        return savedBoard.getId();
    }

    @Override
    @Transactional
    public void updateBoard(Long id, BoardUpdateRequestDTO dto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        board.update(dto);

    }
}
