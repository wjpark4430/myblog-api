package com.blog.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.back.domain.Tag;

public interface TagRepositoy extends JpaRepository<Tag, Long> {

}
