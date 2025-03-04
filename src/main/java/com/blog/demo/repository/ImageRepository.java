package com.blog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.demo.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
