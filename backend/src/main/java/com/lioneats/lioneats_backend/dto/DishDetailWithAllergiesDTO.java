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
public class DishDetailWithAllergiesDTO {

    private String name;
    private Boolean isSpicy;
    private String ingredients;
    private String history;
    private String description;
    private String imageUrl;
    private List<AllergyDTO> allergies; // Add allergies list here
}
