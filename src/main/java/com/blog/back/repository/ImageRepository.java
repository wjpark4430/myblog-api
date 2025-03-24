package com.blog.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.back.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
