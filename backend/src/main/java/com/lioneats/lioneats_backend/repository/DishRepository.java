package com.lioneats.lioneats_backend.repository;

import com.lioneats.lioneats_backend.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

}
