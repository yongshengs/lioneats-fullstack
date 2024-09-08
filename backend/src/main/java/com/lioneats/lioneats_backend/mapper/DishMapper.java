package com.lioneats.lioneats_backend.mapper;

import com.lioneats.lioneats_backend.dto.DishDTO;
import com.lioneats.lioneats_backend.model.Dish;
import com.lioneats.lioneats_backend.model.DishDetail;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {

    public static DishDTO toDTO(Dish dish) {
        if (dish == null) {
            return null;
        }

        DishDTO dishDTO = new DishDTO();
        dishDTO.setId(dish.getId());
        dishDTO.setDishDetailName(dish.getDishDetail().getName());
        dishDTO.setImageUrl(dish.getImageUrl());

        return dishDTO;
    }

    public static Dish toEntity(DishDTO dishDTO, DishDetail dishDetail) {
        if (dishDTO == null) {
            return null;
        }

        Dish dish = new Dish();
        dish.setId(dishDTO.getId());
        dish.setDishDetail(dishDetail);
        dish.setImageUrl(dishDTO.getImageUrl());
        return dish;
    }
}

