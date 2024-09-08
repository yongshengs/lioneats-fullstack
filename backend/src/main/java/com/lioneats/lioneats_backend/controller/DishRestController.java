package com.lioneats.lioneats_backend.controller;

import com.lioneats.lioneats_backend.dto.AllergyDTO;
import com.lioneats.lioneats_backend.dto.DishDTO;
import com.lioneats.lioneats_backend.dto.DishDetailDTO;
import com.lioneats.lioneats_backend.dto.DishDetailWithAllergiesDTO;
import com.lioneats.lioneats_backend.mapper.AllergyMapper;
import com.lioneats.lioneats_backend.mapper.DishDetailMapper;
import com.lioneats.lioneats_backend.mapper.DishMapper;
import com.lioneats.lioneats_backend.model.Allergy;
import com.lioneats.lioneats_backend.model.Dish;
import com.lioneats.lioneats_backend.model.DishDetail;
import com.lioneats.lioneats_backend.service.AllergyService;
import com.lioneats.lioneats_backend.service.DishDetailService;
import com.lioneats.lioneats_backend.service.DishService;
import com.lioneats.lioneats_backend.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/dishes")
public class DishRestController {

    private final DishService dishService;

    private final DishDetailService dishDetailService;

    private final ShopService shopService;

    private final AllergyService allergyService;

    @Autowired
    public DishRestController(DishDetailService dishDetailService, DishService dishService, ShopService shopService, AllergyService allergyService  ) {
        this.dishDetailService = dishDetailService;
        this.dishService = dishService;
        this.shopService = shopService;
        this.allergyService = allergyService;
    }

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDishes() {
        List<DishDTO> dishes = dishService.getAllDishes().stream()
                .map(DishMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("detailsList")
    public ResponseEntity<List<DishDetailDTO>> getAllDishDetails() {
        List<DishDetail> dishes = dishDetailService.getAllDishDetails();
        List<DishDetailDTO> dishDTOs = dishes.stream()
                .map(DishDetailMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dishDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDishById(@PathVariable("id") int id) {
        return dishService.getDishById(id)
                .map(dish -> {
                    DishDetail dishDetail = dish.getDishDetail();
                    DishDetailDTO dishDTO = new DishDetailDTO();
                    dishDTO.setName(dishDetail.getName());
                    dishDTO.setDescription(dishDetail.getDescription());
                    dishDTO.setHistory(dishDetail.getHistory());
                    dishDTO.setIsSpicy(dishDetail.getIsSpicy());
                    dishDTO.setIngredients(dishDetail.getIngredients());
                    dishDTO.setImageUrl(dish.getImageUrl());
                    return ResponseEntity.ok(dishDTO);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/dishNames")
    public ResponseEntity<List<String>> getAllDishNames() {
        List<String> dishNames = dishDetailService.getAllDishNames();
        return ResponseEntity.ok(dishNames);
    }

    @GetMapping("/{id}/details-with-allergies")
    public ResponseEntity<?> getDishWithAllergiesById(@PathVariable("id") int id) {
        return dishService.getDishById(id)
                .map(dish -> {
                    DishDetail dishDetail = dish.getDishDetail();
                    DishDetailWithAllergiesDTO dishDTO = new DishDetailWithAllergiesDTO();

                    dishDTO.setName(dishDetail.getName());
                    dishDTO.setDescription(dishDetail.getDescription());
                    dishDTO.setHistory(dishDetail.getHistory());
                    dishDTO.setIsSpicy(dishDetail.getIsSpicy());
                    dishDTO.setIngredients(dishDetail.getIngredients());
                    dishDTO.setImageUrl(dish.getImageUrl());

                    // Fetch the allergies associated with the dish
                    List<AllergyDTO> allergyDTOs = dishDetail.getAllergies().stream()
                            .map(AllergyMapper::toDTO)
                            .collect(Collectors.toList());

                    dishDTO.setAllergies(allergyDTOs);

                    return ResponseEntity.ok(dishDTO);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/safeDishes")
    public ResponseEntity<List<DishDTO>> getSafeDishes(@RequestBody List<String> allergyNames) {

        List<Allergy> allergies = allergyNames.stream()
                .map(allergyService::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        List<DishDetail> safeDishDetails = shopService.getSafeDishes(allergies);

        List<Dish> safeDishes = safeDishDetails.stream().map(DishDetail::getDish).toList();

        List<DishDTO> safeDishDTOs = safeDishes.stream()
                .map(DishMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(safeDishDTOs);
    }

}
