package com.lioneats.lioneats_backend.repository;

import com.lioneats.lioneats_backend.model.google.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
