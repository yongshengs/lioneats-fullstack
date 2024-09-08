package com.lioneats.lioneats_backend.mapper;

import com.lioneats.lioneats_backend.dto.ML_FeedbackDTO;
import com.lioneats.lioneats_backend.model.ML_Feedback;
import com.lioneats.lioneats_backend.model.DishDetail;
import com.lioneats.lioneats_backend.service.DishDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ML_FeedbackMapper {

    private final DishDetailService dishDetailService;

    @Autowired
    public ML_FeedbackMapper(DishDetailService dishDetailService) {
        this.dishDetailService = dishDetailService;
    }

    public ML_Feedback toEntity(ML_FeedbackDTO feedbackDTO) {
        // Use findByName to get the DishDetail from the name provided in DTO
        DishDetail dishDetail = dishDetailService.findByName(feedbackDTO.getUserDish())
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + feedbackDTO.getUserDish()));

        return new ML_Feedback(
                null, // ID is auto-generated
                feedbackDTO.getImageLocation(),
                feedbackDTO.getMl_result(),
                dishDetail,
                feedbackDTO.getRemarks()
        );
    }
}
