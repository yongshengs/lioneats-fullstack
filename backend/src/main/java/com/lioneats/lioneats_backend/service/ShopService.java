package com.lioneats.lioneats_backend.service;

import com.lioneats.lioneats_backend.dto.SearchRequestDTO;
import com.lioneats.lioneats_backend.dto.google.ShopDTO;
import com.lioneats.lioneats_backend.model.Allergy;
import com.lioneats.lioneats_backend.model.Circle;
import com.lioneats.lioneats_backend.model.DishDetail;
import com.lioneats.lioneats_backend.model.Shop;

import java.util.List;
import java.util.Optional;

public interface ShopService {

    List<ShopDTO> getShopsByCriteria(SearchRequestDTO requestDTO);

    List<ShopDTO> getShopsByLocation(double lat, double lng);

    Optional<Circle> findNearestCircleByLocationName(String mrtName);

    Optional<Circle> findNearestCircle(double lat, double lng);

    Optional<Shop> getShopById(Long id);

    List<DishDetail> getSafeDishes(List<Allergy> allergies);
}

