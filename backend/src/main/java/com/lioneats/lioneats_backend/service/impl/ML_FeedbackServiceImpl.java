package com.lioneats.lioneats_backend.service.impl;

import com.lioneats.lioneats_backend.model.ML_Feedback;
import com.lioneats.lioneats_backend.repository.ML_FeedbackRepository;
import com.lioneats.lioneats_backend.service.ML_FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ML_FeedbackServiceImpl implements ML_FeedbackService {

    private final ML_FeedbackRepository mlFeedbackRepository;

    @Autowired
    public ML_FeedbackServiceImpl(ML_FeedbackRepository mlFeedbackRepository) {
        this.mlFeedbackRepository = mlFeedbackRepository;
    }

    @Override
    public ML_Feedback createFeedback(ML_Feedback feedback) {
        return mlFeedbackRepository.save(feedback);
    }

    @Override
    public List<ML_Feedback> getAllFeedbacks() {
        return mlFeedbackRepository.findAll();
    }

    @Override
    public Optional<ML_Feedback> getFeedbackById(Long id) {
        return mlFeedbackRepository.findById(id);
    }

    @Override
    public void deleteFeedback(Long id) {
        mlFeedbackRepository.deleteById(id);
    }

    @Override
    public ML_Feedback updateFeedback(ML_Feedback feedback) {
        return mlFeedbackRepository.save(feedback);
    }

    @Override
    public void saveFeedback(ML_Feedback feedback) {

    }
}
