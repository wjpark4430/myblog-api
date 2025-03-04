package com.blog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.demo.domain.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

}
