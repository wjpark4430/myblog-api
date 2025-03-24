package com.blog.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.back.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
