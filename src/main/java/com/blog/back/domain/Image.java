package com.blog.back.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFilename;

    private String savedFilename;

    @OneToMany(mappedBy = "image")
    private List<BoardImage> boardImages;

    private Image(String originalFilename, String savedFilename) {
        this.originalFilename = originalFilename;
        this.savedFilename = savedFilename;
    }

    public static Image ofCreate(String originalFilename, String savedFilename) {
        return new Image(originalFilename, savedFilename);
    }

}
