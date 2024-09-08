package com.lioneats.lioneats_backend.service;

import com.lioneats.lioneats_backend.model.Allergy;
import java.util.List;
import java.util.Optional;

public interface AllergyService {

    List<Allergy> getAllAllergies();

    Optional<Allergy> getAllergyById(Long id);

    Allergy createAllergy(Allergy allergy);

    Allergy updateAllergy(Long id, Allergy allergyDetails);

    void deleteAllergy(Long id);

    List<Allergy> findByNameIn(List<String> names);

    Optional<Allergy> findByName(String name);

    List<String> getAllAllergyNames();
}
