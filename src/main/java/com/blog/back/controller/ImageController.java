package com.blog.back.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.back.service.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable("filename") String filename) {
        Path path = Paths.get("uploads/images").resolve(filename);
        FileSystemResource resource = new FileSystemResource(path);

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType;
        try {
            contentType = Files.probeContentType(path);
        } catch (IOException e) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Default to binary if type cannot be determined
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) {
        try {
            String imageUrl = imageService.saveImage(file);

            // CKEditor는 JSON 응답 형식이 정해져 있음 (uploaded, url)
            Map<String, Object> response = new HashMap<>();
            response.put("uploaded", true);
            response.put("url", imageUrl);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 실패 응답 (CKEditor는 uploaded: false 필요)
            Map<String, Object> error = new HashMap<>();
            error.put("uploaded", false);
            error.put("error", Map.of("message", e.getMessage()));

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
