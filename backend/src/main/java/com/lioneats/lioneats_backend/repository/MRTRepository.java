package com.lioneats.lioneats_backend.repository;

import com.lioneats.lioneats_backend.model.MRT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MRTRepository extends JpaRepository<MRT, Long> {

    @Query("SELECT m FROM MRT m WHERE m.name = :name")
    MRT findByName(@Param("name") String name);

    List<MRT> findByLatitudeAndLongitude(double latitude, double longitude);


}


