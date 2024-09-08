package com.lioneats.lioneats_backend.repository;

import com.lioneats.lioneats_backend.model.google.OpeningHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHourRepository extends JpaRepository<OpeningHour, Long> {
}
