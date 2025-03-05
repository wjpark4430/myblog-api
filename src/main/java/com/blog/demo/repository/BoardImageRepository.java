package com.blog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.demo.domain.BoardImage;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

}
