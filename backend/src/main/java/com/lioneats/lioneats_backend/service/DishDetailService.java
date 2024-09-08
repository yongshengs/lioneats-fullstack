package com.lioneats.lioneats_backend.service;

import java.util.List;
import java.util.Optional;

import com.lioneats.lioneats_backend.model.DishDetail;

public interface DishDetailService {

    List<DishDetail> getAllDishDetails();

    Optional<DishDetail> findByName(String name);

    Optional<DishDetail> getDishDetailByName(String name);

    DishDetail saveDishDetail(DishDetail dishDetail);

    List<DishDetail> saveAllDishDetails(List<DishDetail> dishDetails);

    List<DishDetail> findByNameIn(List<String> names);

    void deleteDishDetailByName(String name);

    List<String> getAllDishNames();
}


