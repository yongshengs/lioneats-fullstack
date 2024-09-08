package com.lioneats.lioneats_backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lioneats.lioneats_backend.service.*;
import com.lioneats.lioneats_backend.model.Allergy;
import com.lioneats.lioneats_backend.repository.AllergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllergyServiceImpl implements AllergyService {

    private final AllergyRepository allergyRepository;

    @Autowired
    public AllergyServiceImpl(AllergyRepository allergyRepository) {
        this.allergyRepository = allergyRepository;
    }

    @Override
    public List<Allergy> getAllAllergies() {
        return allergyRepository.findAll();
    }

    @Override
    public Optional<Allergy> getAllergyById(Long id) {
        return allergyRepository.findById(id);
    }

    @Override
    public Allergy createAllergy(Allergy allergy) {
        Optional<Allergy> existingAllergy = allergyRepository.findByName(allergy.getName());
        if (existingAllergy.isPresent()) {
            throw new IllegalArgumentException("Allergy with name '" + allergy.getName() + "' already exists.");
        }
        return allergyRepository.save(allergy);
    }

    @Override
    public Allergy updateAllergy(Long id, Allergy allergyDetails) {
        return allergyRepository.findById(id).map(allergy -> {
            allergy.setName(allergyDetails.getName());
            allergy.setDishes(allergyDetails.getDishes());
            allergy.setUsers(allergyDetails.getUsers());
            return allergyRepository.save(allergy);
        }).orElseGet(() -> {
            allergyDetails.setId(id);
            return allergyRepository.save(allergyDetails);
        });
    }

    @Override
    public void deleteAllergy(Long id) {
        allergyRepository.deleteById(id);
    }

    @Override
    public List<Allergy> findByNameIn(List<String> names) {
        return allergyRepository.findByNameIn(names);
    }

    @Override
    public Optional<Allergy> findByName(String name) {
        return allergyRepository.findByName(name);
    }

    @Override
    public List<String> getAllAllergyNames() {
        List<Allergy> allergies = allergyRepository.findAll();
        return allergies.stream()
                .map(Allergy::getName)
                .collect(Collectors.toList());
    }
}
