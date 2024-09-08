package com.lioneats.lioneats_backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDTO {

    private List<String> location;         // MRT station
    private List<String> allergies;  // Selected allergies
    private String budget;           // Budget level (HIGH, MEDIUM, LOW)
    private List<String> dishes;     // Selected dishes
    private double minRating;        // Minimum rating for filtering

}
