package com.lioneats.lioneats_backend.controller;

import com.lioneats.lioneats_backend.dto.UserLocationDTO;
import com.lioneats.lioneats_backend.dto.SearchRequestDTO;
import com.lioneats.lioneats_backend.dto.google.ShopDTO;
import com.lioneats.lioneats_backend.model.MRT;
import com.lioneats.lioneats_backend.service.MRTService;
import com.lioneats.lioneats_backend.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/feed")
public class FeedController {

    private final ShopService shopService;
    private final MRTService mrtService;

    @Autowired
    public FeedController(ShopService shopService, MRTService mrtService) {
        this.shopService = shopService;
        this.mrtService = mrtService;
    }

    @PostMapping("/default")
    public ResponseEntity<List<ShopDTO>> getShops(@RequestBody(required = false) UserLocationDTO locationDTO) {
        double lat;
        double lng;

        if (locationDTO == null) {
            MRT defaultMRT = mrtService.getMRTStationById(12L)
                    .orElseThrow(() -> new RuntimeException("Default MRT station not found"));
            lat = defaultMRT.getLatitude();
            lng = defaultMRT.getLongitude();
        } else {
            lat = locationDTO.getLatitude();
            lng = locationDTO.getLongitude();
        }

        List<ShopDTO> shops = shopService.getShopsByLocation(lat, lng);
        if (shops.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(shops);
        }
        return ResponseEntity.ok(shops);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ShopDTO>> filterShops(@RequestBody SearchRequestDTO filterRequestDTO) {
        List<ShopDTO> shops = shopService.getShopsByCriteria(filterRequestDTO);
        if (shops.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(shops);
        }
        return ResponseEntity.ok(shops);
    }

}
