package com.lioneats.lioneats_backend.service.impl;

import java.util.List;
import java.util.Optional;

import com.lioneats.lioneats_backend.model.Circle;
import com.lioneats.lioneats_backend.repository.CircleRepository;
import com.lioneats.lioneats_backend.service.CircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CircleServiceImpl implements CircleService {

    private final CircleRepository circleRepository;

    @Autowired
    public CircleServiceImpl(CircleRepository circleRepository) {
        this.circleRepository = circleRepository;
    }

    @Override
    public List<Circle> getAllCircles() {
        return circleRepository.findAll();
    }

    @Override
    public Optional<Circle> getCircleById(Long id) {
        return circleRepository.findById(id);
    }

    @Override
    public Circle saveCircle(Circle circle) {
        return circleRepository.save(circle);
    }

    @Override
    public List<Circle> saveAllCircles(List<Circle> circles) {
        return circleRepository.saveAll(circles);
    }

    @Override
    public void deleteCircleById(Long id) {
        circleRepository.deleteById(id);
    }
}
