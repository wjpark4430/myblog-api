package com.blog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.demo.domain.BoardTag;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {

}
