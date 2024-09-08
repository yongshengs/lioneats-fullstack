package com.lioneats.lioneats_backend;

import com.lioneats.lioneats_backend.model.Shop;
import com.lioneats.lioneats_backend.model.google.OpeningHour;
import com.lioneats.lioneats_backend.repository.OpeningHourRepository;
import com.lioneats.lioneats_backend.service.OpeningHourService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class OpeningHourServiceImplTest {

    @Autowired
    private OpeningHourService openingHourService;

    @Autowired
    private OpeningHourRepository openingHourRepository;

    @Test
    @Transactional
    public void testSaveOpeningHour() {

        OpeningHour openingHour = new OpeningHour();
        openingHour.setShop(new Shop());


        OpeningHour savedOpeningHour = openingHourRepository.save(openingHour);


        Optional<OpeningHour> retrievedOpeningHour = openingHourRepository.findById(savedOpeningHour.getId());
        assertThat(retrievedOpeningHour.isPresent()).isTrue();
        assertThat(retrievedOpeningHour.get().getId()).isEqualTo(openingHour.getId());
    }

    @Test
    @Transactional
    public void testFindById() {

        OpeningHour openingHour = new OpeningHour();
        openingHour.setShop(new Shop());
        OpeningHour savedOpeningHour = openingHourRepository.save(openingHour);


        Optional<OpeningHour> foundOpeningHour = openingHourRepository.findById(savedOpeningHour.getId());


        assertThat(foundOpeningHour.isPresent()).isTrue();
        assertThat(foundOpeningHour.get().getId()).isEqualTo(savedOpeningHour.getId());
    }
}