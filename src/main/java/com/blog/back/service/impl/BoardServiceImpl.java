package com.blog.back.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.back.domain.Board;
import com.blog.back.dto.BoardDTO;
import com.blog.back.repository.BoardRepository;
import com.blog.back.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BoardDTO> getBoards() {
        List<Board> boards = boardRepository.findAll();

        return boards.stream()
            .map(board -> new BoardDTO(board.getTitle(), board.getContent()))
            .toList();
    }
}
