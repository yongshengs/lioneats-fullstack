package com.lioneats.lioneats_backend.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllergyDTO {

    private Long id;
    private String name;
    private List<DishDetailDTO> dishes;
}
