package com.blog.back.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.back.domain.Image;
import com.blog.back.repository.ImageRepository;
import com.blog.back.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    public static final Path uploadDir = Paths.get("uploads/images");

    @Override
    public String saveImage(MultipartFile file) {
        // 저장 파일명
        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String savedName = uuid + "_" + originalName;

        // 저장 경로 생성
        Path savePath = uploadDir.resolve(savedName);
        try {
            Files.createDirectories(savePath.getParent());
        } catch (IOException e) {
            log.error("Failed to create directory: {}", e.getMessage());
            throw new RuntimeException("Failed to create directory", e);
        } // 디렉토리 없으면 생성

        // 실제 파일 저장
        try {
            file.transferTo(savePath);
        } catch (IllegalStateException | IOException e) {
            log.error("Failed to save file: {}", e.getMessage());
            throw new RuntimeException("Failed to save file", e);
        }

        // DB 저장
        Image image = Image.ofCreate(originalName, savedName);

        Image savedImage = imageRepository.save(image);

        // 반환할 URL
        return String.format("/images/%s", savedImage.getSavedFilename());
    }
}
