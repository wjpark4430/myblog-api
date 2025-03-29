package com.blog.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.back.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    
    @Modifying
    @Query("UPDATE Board b SET b.likeCount = :likeCount WHERE b.id = :boardId")
    void updateLikeCount(@Param("boardId") Long boardId, @Param("likeCount") Integer likeCount);

}
