package com.lioneats.lioneats_backend.controller;

import com.lioneats.lioneats_backend.dto.google.ShopDTO;
import com.lioneats.lioneats_backend.mapper.google.ShopMapper;
import com.lioneats.lioneats_backend.model.Shop;
import com.lioneats.lioneats_backend.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/shop")
@RestController
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShop(@PathVariable("id") Long id) {
        Optional<Shop> shopOptional = shopService.getShopById(id);

        if (shopOptional.isPresent()) {
            ShopDTO shopDTO = ShopMapper.toDTO(shopOptional.get());
            return ResponseEntity.ok(shopDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shop not found");
        }
    }

}
