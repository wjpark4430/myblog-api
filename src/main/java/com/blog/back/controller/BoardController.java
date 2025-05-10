package com.blog.back.controller;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.back.dto.board.BoardCreateRequestDTO;
import com.blog.back.dto.board.BoardListResponseDTO;
import com.blog.back.dto.board.BoardResponseDTO;
import com.blog.back.dto.board.BoardUpdateRequestDTO;
import com.blog.back.service.BoardService;
import com.blog.back.service.LikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final LikeService likeService;

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

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBoard(@PathVariable("id") Long id,
            @RequestBody BoardUpdateRequestDTO dto) {
        boardService.updateBoard(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/like")
    public ResponseEntity<Long> getLikeCount(@PathVariable("id") Long id) {
        long count = likeService.getLikeCount(id);
        return ResponseEntity.ok().body(count);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Long> updateLike(@PathVariable("id") Long id) {
        likeService.addLike(id, 1L);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<Long> deleteLike(@PathVariable("id") Long id) {
        likeService.removeLike(id, 1L);

        return ResponseEntity.ok().build();
    }

}
