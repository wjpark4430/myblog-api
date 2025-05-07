package com.blog.back.service.impl;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.back.domain.Board;
import com.blog.back.domain.BoardImage;
import com.blog.back.domain.Image;
import com.blog.back.dto.board.BoardCreateRequestDTO;
import com.blog.back.dto.board.BoardListResponseDTO;
import com.blog.back.dto.board.BoardResponseDTO;
import com.blog.back.dto.board.BoardUpdateRequestDTO;
import com.blog.back.repository.BoardImageRepository;
import com.blog.back.repository.BoardRepository;
import com.blog.back.repository.ImageRepository;
import com.blog.back.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final ImageRepository imageRepository;

    @Override
    @Transactional(readOnly = true)
    public BoardListResponseDTO getBoards(Pageable pageable) {
        Long totalCount = boardRepository.count();
        List<Board> boards = boardRepository.findAll(pageable).getContent();

        return BoardListResponseDTO.fromBoards(boards, totalCount, pageable.getPageSize(), pageable.getPageNumber());
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
        String sanitizedContent = Jsoup.clean(dto.getContent(), Safelist.basicWithImages());
        Board board = Board.ofCreate(dto.getTitle(), sanitizedContent);

        Board savedBoard = boardRepository.save(board);

        // 1. content 내에서 이미지 URL 추출
        List<String> imageUrls = extractImageUrls(savedBoard.getContent()); // "/image/UUID_filename.jpg"

        // 2. 해당 URL에 해당하는 이미지 객체 조회해서 게시글에 연결
        for (String url : imageUrls) {
            String filename = extractFilenameFromUrl(url); // ex) "UUID_filename.jpg"
            Image savedImage = imageRepository.findBySavedFilename(filename)
                    .orElseThrow(() -> new RuntimeException("Image not found"));
            BoardImage boardImage = BoardImage.ofCreate(savedBoard, savedImage);
            boardImageRepository.save(boardImage);
        }

        return savedBoard.getId();
    }

    @Override
    @Transactional
    public void updateBoard(Long id, BoardUpdateRequestDTO dto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        board.update(dto);

    }

    @Override
    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        boardRepository.delete(board);
    }

    private List<String> extractImageUrls(String content) {
        List<String> urls = new ArrayList<>();
        Pattern pattern = Pattern.compile("<img[^>]+src=[\"'](/image/[^\"']+)[\"']");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            urls.add(matcher.group(1));
        }

        return urls;
    }

    private String extractFilenameFromUrl(String url) {
        return Paths.get(url).getFileName().toString();
    }

}
