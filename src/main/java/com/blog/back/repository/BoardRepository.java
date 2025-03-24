package com.blog.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.back.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
