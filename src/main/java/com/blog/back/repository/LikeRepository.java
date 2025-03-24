package com.blog.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.back.domain.BoardLike;

public interface LikeRepository extends JpaRepository<BoardLike, Long> {

}
