package com.lioneats.lioneats_backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ML_FeedbackDTO {

    private String imageLocation;
    private String ml_result;
    private String userDish; // This will hold the name of the dish selected by the user
    private String remarks;
}
