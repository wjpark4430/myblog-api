package com.blog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.demo.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
