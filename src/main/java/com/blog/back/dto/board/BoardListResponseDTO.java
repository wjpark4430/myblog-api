package com.blog.back.dto.board;

import java.util.List;
import java.util.stream.Collectors;

import com.blog.back.domain.Board;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardListResponseDTO {

    private List<BoardDTO> boards;
    private long totalCount;
    private int pageSize;
    private int currentPage;
    private int totalPages;

    @Builder
    private BoardListResponseDTO(List<BoardDTO> boards, long totalCount, int pageSize, int currentPage, int totalPages) {
        this.boards = boards;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public static BoardListResponseDTO fromBoards(List<Board> boards, long totalCount, int pageSize, int currentPage) {
        List<BoardDTO> boardDTOs = boards.stream()
                .map(board -> BoardDTO.fromEntity(board))
                .collect(Collectors.toList());
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        return BoardListResponseDTO.builder()
                .boards(boardDTOs)
                .totalCount(totalCount)
                .pageSize(pageSize)
                .currentPage(currentPage)
                .totalPages(totalPages)
                .build();
    }
}
