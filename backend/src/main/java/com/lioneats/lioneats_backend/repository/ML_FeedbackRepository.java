package com.lioneats.lioneats_backend.repository;

import com.lioneats.lioneats_backend.model.ML_Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ML_FeedbackRepository extends JpaRepository<ML_Feedback, Long> {
}
