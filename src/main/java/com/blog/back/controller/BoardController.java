package com.blog.back.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.back.dto.BoardDTO;
import com.blog.back.service.BoardService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    
    @GetMapping
    public ResponseEntity<List<BoardDTO>> getBoards() {
        return ResponseEntity.ok().body(boardService.getBoards());
    }
    
}
