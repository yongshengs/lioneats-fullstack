package com.lioneats.lioneats_backend;

import com.lioneats.lioneats_backend.model.Allergy;
import com.lioneats.lioneats_backend.repository.AllergyRepository;
import com.lioneats.lioneats_backend.service.impl.AllergyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AllergyServiceImplTest {

    @Autowired
    private AllergyServiceImpl allergyService;

    @Autowired
    private AllergyRepository allergyRepository;

    @BeforeEach
    void setUp() {
        allergyRepository.deleteAll();
    }

    @Test
    void testGetAllAllergies() {
        Allergy allergy1 = new Allergy(null, "Peanuts", null, null);
        Allergy allergy2 = new Allergy(null, "Shellfish", null, null);
        allergyRepository.saveAll(Arrays.asList(allergy1, allergy2));

        List<Allergy> result = allergyService.getAllAllergies();
        assertEquals(2, result.size());
        assertEquals("Peanuts", result.get(0).getName());
        assertEquals("Shellfish", result.get(1).getName());
    }

    @Test
    void testGetAllergyById() {
        Allergy allergy = new Allergy(null, "Peanuts", null, null);
        Allergy savedAllergy = allergyRepository.save(allergy);

        Optional<Allergy> result = allergyService.getAllergyById(savedAllergy.getId());
        assertTrue(result.isPresent());
        assertEquals("Peanuts", result.get().getName());
    }

    @Test
    void testCreateAllergy() {
        Allergy allergy = new Allergy(null, "Peanuts", null, null);

        Allergy result = allergyService.createAllergy(allergy);
        assertNotNull(result.getId());
        assertEquals("Peanuts", result.getName());
    }

    @Test
    void testUpdateAllergy() {
        Allergy allergy = new Allergy(null, "Peanuts", null, null);
        Allergy savedAllergy = allergyRepository.save(allergy);

        Allergy updatedDetails = new Allergy(null, "Tree Nuts", null, null);

        Allergy result = allergyService.updateAllergy(savedAllergy.getId(), updatedDetails);
        assertEquals("Tree Nuts", result.getName());
    }

    @Test
    void testDeleteAllergy() {
        Allergy allergy = new Allergy(null, "Peanuts", null, null);
        Allergy savedAllergy = allergyRepository.save(allergy);

        allergyService.deleteAllergy(savedAllergy.getId());

        Optional<Allergy> result = allergyRepository.findById(savedAllergy.getId());
        assertFalse(result.isPresent());
    }

    @Test
    void testFindByNameIn() {
        Allergy allergy1 = new Allergy(null, "Peanuts", null, null);
        Allergy allergy2 = new Allergy(null, "Seafood", null, null);
        allergyRepository.saveAll(Arrays.asList(allergy1, allergy2));

        List<String> names = Arrays.asList("Peanuts", "Seafood");
        List<Allergy> result = allergyService.findByNameIn(names);
        assertEquals(2, result.size());
        assertEquals("Peanuts", result.get(0).getName());
        assertEquals("Seafood", result.get(1).getName());
    }

    @Test
    void testFindByName() {
        Allergy allergy = new Allergy(null, "Peanuts", null, null);
        allergyRepository.save(allergy);

        Optional<Allergy> result = allergyService.findByName("Peanuts");
        assertTrue(result.isPresent());
        assertEquals("Peanuts", result.get().getName());
    }
}
