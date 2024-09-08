package com.lioneats.lioneats_backend.controller;

import com.lioneats.lioneats_backend.dto.AllergyDTO;
import com.lioneats.lioneats_backend.mapper.AllergyMapper;
import com.lioneats.lioneats_backend.model.Allergy;
import com.lioneats.lioneats_backend.service.AllergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/allergies")
public class AllergyController {

    private final AllergyService allergyService;

    @Autowired
    public AllergyController(AllergyService allergyService) {
        this.allergyService = allergyService;
    }

    @GetMapping
    public ResponseEntity<List<AllergyDTO>> getAllAllergies() {
        List<Allergy> allergies = allergyService.getAllAllergies();
        List<AllergyDTO> allergyDTOs = allergies.stream()
                .map(AllergyMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allergyDTOs);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllergyNames() {
        List<String> allergyNames = allergyService.getAllAllergyNames();
        return ResponseEntity.ok(allergyNames);
    }
}
