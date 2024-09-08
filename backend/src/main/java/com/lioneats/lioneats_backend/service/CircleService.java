package com.lioneats.lioneats_backend.service;

import com.lioneats.lioneats_backend.model.Circle;

import java.util.List;
import java.util.Optional;

public interface CircleService {

    List<Circle> getAllCircles();

    Optional<Circle> getCircleById(Long id);

    Circle saveCircle(Circle circle);

    List<Circle> saveAllCircles(List<Circle> circles);

    void deleteCircleById(Long id);
}
