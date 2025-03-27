package com.blog.back.controller;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.back.dto.BoardCreateRequestDTO;
import com.blog.back.dto.BoardListResponseDTO;
import com.blog.back.dto.BoardResponseDTO;
import com.blog.back.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<BoardListResponseDTO> getBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        BoardListResponseDTO response = boardService
                .getBoards(PageRequest.of(page, size, Sort.by("createdAt").descending()));

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> getBoard(@PathVariable("id") Long id) {
        BoardResponseDTO dto = boardService.getBoard(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> createBoard(@RequestBody BoardCreateRequestDTO dto) {
        Long id = boardService.createBoard(dto);

        // Consider scalability
        Map<String, Long> response = Map.of("id", id);

        return ResponseEntity.status(201).body(response);
    }

}
