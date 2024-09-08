package com.lioneats.lioneats_backend.repository;

import com.lioneats.lioneats_backend.model.DishDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishDetailRepository extends JpaRepository<DishDetail, String> {
    List<DishDetail> findByNameIn(List<String> names);
    Optional<DishDetail> findByName(String name);

    @Query("SELECT d FROM DishDetail d JOIN Shop s ON LOWER(s.keyword) = LOWER(d.name) WHERE LOWER(s.keyword) = LOWER(:keyword)")
    DishDetail findDishDetailByShopKeyword(@Param("keyword") String keyword);

}