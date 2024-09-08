package com.lioneats.lioneats_backend.controller;

import com.lioneats.lioneats_backend.dto.ML_FeedbackDTO;
import com.lioneats.lioneats_backend.mapper.ML_FeedbackMapper;
import com.lioneats.lioneats_backend.model.ML_Feedback;
import com.lioneats.lioneats_backend.service.ML_FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class MLFeedbackController {

    private final ML_FeedbackService mlFeedbackService;
    private final ML_FeedbackMapper mlFeedbackMapper;

    @Autowired
    public MLFeedbackController(ML_FeedbackService mlFeedbackService, ML_FeedbackMapper mlFeedbackMapper) {
        this.mlFeedbackService = mlFeedbackService;
        this.mlFeedbackMapper = mlFeedbackMapper;
    }

    @PostMapping
    public ResponseEntity<String> submitFeedback(@RequestBody ML_FeedbackDTO feedbackDTO) {
        try {
            ML_Feedback feedback = mlFeedbackMapper.toEntity(feedbackDTO);
            mlFeedbackService.createFeedback(feedback);
            return ResponseEntity.ok("Feedback submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Feedback submission failed: " + e.getMessage());
        }
    }
}
