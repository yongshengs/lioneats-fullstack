package com.lioneats.lioneats_backend.service.impl;

import java.util.List;
import java.util.Optional;

import com.lioneats.lioneats_backend.model.Dish;
import com.lioneats.lioneats_backend.repository.DishRepository;
import com.lioneats.lioneats_backend.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    @Override
    public Optional<Dish> getDishById(int id) {
        return dishRepository.findById(id);
    }

    @Override
    public Dish createDish(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public List<Dish> createAllDishes(List<Dish> dishes) {
        return dishRepository.saveAll(dishes);
    }

    @Override
    public void deleteDish(Integer id) {
        dishRepository.deleteById(id);
    }
}
