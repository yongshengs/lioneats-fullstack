package com.lioneats.lioneats_backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lioneats.lioneats_backend.model.DishDetail;
import com.lioneats.lioneats_backend.repository.DishDetailRepository;
import com.lioneats.lioneats_backend.service.DishDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishDetailServiceImpl implements DishDetailService {

    private final DishDetailRepository dishDetailRepository;

    @Autowired
    public DishDetailServiceImpl(DishDetailRepository dishDetailRepository) {
        this.dishDetailRepository = dishDetailRepository;
    }

    @Override
    public List<DishDetail> getAllDishDetails() {
        return dishDetailRepository.findAll();
    }

    @Override
    public Optional<DishDetail> findByName(String name) {
        return dishDetailRepository.findByName(name);
    }

    @Override
    public Optional<DishDetail> getDishDetailByName(String name) {
        return dishDetailRepository.findById(name);
    }

    @Override
    public DishDetail saveDishDetail(DishDetail dishDetail) {
        return dishDetailRepository.save(dishDetail);
    }

    @Override
    public List<DishDetail> saveAllDishDetails(List<DishDetail> dishDetails) {
        return dishDetailRepository.saveAll(dishDetails);
    }

    @Override
    public List<DishDetail> findByNameIn(List<String> names) {
        return dishDetailRepository.findByNameIn(names);
    }

    @Override
    public void deleteDishDetailByName(String name) {
        dishDetailRepository.deleteById(name);
    }

    @Override
    public List<String> getAllDishNames() {
        List<DishDetail> dishes = dishDetailRepository.findAll();
        return dishes.stream()
                .map(DishDetail::getName)
                .collect(Collectors.toList());
    }
}

