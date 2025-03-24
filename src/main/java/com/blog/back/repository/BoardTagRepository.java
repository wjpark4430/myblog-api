package com.blog.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.back.domain.BoardTag;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {

}
