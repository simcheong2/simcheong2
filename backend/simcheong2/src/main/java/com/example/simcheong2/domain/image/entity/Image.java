package com.example.simcheong2.domain.image.entity;

import com.example.simcheong2.domain.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Image {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;

    @Column
    private String fileUrl;

    @Column(columnDefinition = "longtext")
    private String imageText;

    @Column
    private Integer imageIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Image(String fileUrl, String imageText, Integer imageIndex) {
        this.fileUrl = fileUrl;
        this.imageText = imageText;
        this.imageIndex = imageIndex;
    }

    public void updatePost(Post post) {
        this.post = post;
    }
}
