package com.lioneats.lioneats_backend.service.impl;

import java.util.List;

import com.lioneats.lioneats_backend.model.Circle;
import com.lioneats.lioneats_backend.repository.CircleRepository;
import com.lioneats.lioneats_backend.repository.MRTRepository;
import com.lioneats.lioneats_backend.service.CircleMRTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CircleMRTServiceImpl implements CircleMRTService {

    private final MRTRepository mrtRepository;
    private final CircleRepository circleRepository;

    @Autowired
    public CircleMRTServiceImpl(MRTRepository mrtRepository, CircleRepository circleRepository) {
        this.mrtRepository = mrtRepository;
        this.circleRepository = circleRepository;
    }

    @Override
    public List<Circle> getAllCircles() {
        return circleRepository.findAll();
    }

    @Override
    public void deleteAllData() {
        circleRepository.deleteAll(); // First delete circles
        mrtRepository.deleteAll(); // Then delete MRTs
    }
}

