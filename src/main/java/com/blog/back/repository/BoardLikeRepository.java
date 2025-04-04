package com.blog.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.back.domain.BoardLike;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long>{

    void deleteByBoardIdAndMemberId(Long boardId, Long memberId);

}
