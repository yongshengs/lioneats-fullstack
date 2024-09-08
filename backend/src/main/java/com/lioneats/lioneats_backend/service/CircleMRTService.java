package com.lioneats.lioneats_backend.service;

import com.lioneats.lioneats_backend.model.Circle;

import java.util.List;

public interface CircleMRTService {

    List<Circle> getAllCircles();

    void deleteAllData();
}
