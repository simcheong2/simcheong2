package com.example.simcheong2.domain.image.repository;

import com.example.simcheong2.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Integer> {
}