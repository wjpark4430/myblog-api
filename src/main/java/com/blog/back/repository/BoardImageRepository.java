package com.blog.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.back.domain.BoardImage;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

}
