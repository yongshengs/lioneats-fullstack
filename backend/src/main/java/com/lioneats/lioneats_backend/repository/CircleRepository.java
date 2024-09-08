package com.lioneats.lioneats_backend.repository;

import java.util.Optional;

import com.lioneats.lioneats_backend.model.Circle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CircleRepository extends JpaRepository<Circle, Long> {
    @Query("SELECT c FROM Circle c WHERE LOWER(c.mrt.name) = LOWER(:mrtName)")
    Optional<Circle> findByMrtNameIgnoreCase(@Param("mrtName") String mrtName);
}
