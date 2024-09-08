package com.lioneats.lioneats_backend.repository;

import com.lioneats.lioneats_backend.model.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {
    List<Allergy> findByNameIn(List<String> names);

    @Query("SELECT a FROM Allergy a WHERE a.name = :name")
    Optional<Allergy> findByName(@Param("name") String name);

}