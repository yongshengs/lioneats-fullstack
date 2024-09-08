package com.lioneats.lioneats_backend.service.impl;

import com.lioneats.lioneats_backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl {
    @Autowired
    private ReviewRepository reviewRepository;

}
