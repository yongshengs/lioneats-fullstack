package com.lioneats.lioneats_backend.mapper;

import com.lioneats.lioneats_backend.dto.*;
import com.lioneats.lioneats_backend.model.*;

public class DishDetailMapper {

    public static DishDetail toEntity(DishDetailDTO dishDetailDTO) {
        if (dishDetailDTO == null) {
            return null;
        }

        DishDetail dishDetail = new DishDetail();
        dishDetail.setName(dishDetailDTO.getName());
        dishDetail.setIsSpicy(dishDetailDTO.getIsSpicy());
        dishDetail.setIngredients(dishDetailDTO.getIngredients());
        dishDetail.setHistory(dishDetailDTO.getHistory());
        dishDetail.setDescription(dishDetailDTO.getDescription());

        return dishDetail;
    }

    public static DishDetailDTO toDTO(DishDetail dishDetail) {
        if (dishDetail == null) {
            return null;
        }
        return new DishDetailDTO(
                dishDetail.getName(),
                dishDetail.getIsSpicy(),
                dishDetail.getIngredients(),
                dishDetail.getHistory(),
                dishDetail.getDescription(),
                ""
        );
    }
}

