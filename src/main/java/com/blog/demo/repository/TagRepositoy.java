package com.blog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.demo.domain.Tag;

public interface TagRepositoy extends JpaRepository<Tag, Long> {

}
