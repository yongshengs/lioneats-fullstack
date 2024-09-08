package com.lioneats.lioneats_backend.service;

import com.lioneats.lioneats_backend.model.Dish;

import java.util.List;
import java.util.Optional;

public interface DishService {

    List<Dish> getAllDishes();

    Optional<Dish> getDishById(int id);

    Dish createDish(Dish dish);

    List<Dish> createAllDishes(List<Dish> dishes);

    void deleteDish(Integer id);
}

