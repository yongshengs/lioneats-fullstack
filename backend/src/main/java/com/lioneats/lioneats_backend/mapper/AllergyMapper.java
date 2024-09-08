package com.lioneats.lioneats_backend.mapper;

import com.lioneats.lioneats_backend.dto.*;
import com.lioneats.lioneats_backend.model.Allergy;
import com.lioneats.lioneats_backend.model.DishDetail;


import java.util.List;
import java.util.stream.Collectors;

public class AllergyMapper {

    public static Allergy toEntity(AllergyDTO allergyDTO) {
        if (allergyDTO == null) {
            return null;
        }

        Allergy allergy = new Allergy();
        allergy.setId(allergyDTO.getId());
        allergy.setName(allergyDTO.getName());

        List<DishDetail> dishes = allergyDTO.getDishes().stream()
                .map(DishDetailMapper::toEntity)
                .collect(Collectors.toList());
        allergy.setDishes(dishes);

        return allergy;
    }

    public static AllergyDTO toDTO(Allergy allergy) {
        if (allergy == null) {
            return null;
        }

        List<DishDetailDTO> dishDTOs = allergy.getDishes().stream()
                .map(DishDetailMapper::toDTO)
                .collect(Collectors.toList());

        return new AllergyDTO(
                allergy.getId(),
                allergy.getName(),
                dishDTOs
        );
    }
}

