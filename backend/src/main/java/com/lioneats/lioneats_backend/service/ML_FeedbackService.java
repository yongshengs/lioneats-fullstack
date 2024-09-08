package com.lioneats.lioneats_backend.service;

import com.lioneats.lioneats_backend.model.ML_Feedback;

import java.util.List;
import java.util.Optional;

public interface ML_FeedbackService {

    ML_Feedback createFeedback(ML_Feedback feedback);

    List<ML_Feedback> getAllFeedbacks();

    Optional<ML_Feedback> getFeedbackById(Long id);

    void deleteFeedback(Long id);

    ML_Feedback updateFeedback(ML_Feedback feedback);

    void saveFeedback(ML_Feedback feedback);
}
