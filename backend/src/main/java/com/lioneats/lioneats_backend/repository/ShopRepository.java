package com.lioneats.lioneats_backend.repository;

import com.lioneats.lioneats_backend.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Query("SELECT s FROM Shop s WHERE s.circle.id = :circleId")
    List<Shop> findByCircleId(@Param("circleId") Long circleId);
}
