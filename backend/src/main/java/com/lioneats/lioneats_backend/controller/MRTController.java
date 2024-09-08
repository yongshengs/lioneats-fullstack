package com.lioneats.lioneats_backend.controller;

import com.lioneats.lioneats_backend.dto.MRTDTO;
import com.lioneats.lioneats_backend.dto.UserLocationDTO;
import com.lioneats.lioneats_backend.service.MRTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrt")
public class MRTController {

    @Autowired
    private MRTService mrtService;

    @GetMapping("/all")
    public ResponseEntity<List<MRTDTO>> getMRTList() {
        List<MRTDTO> allMRTs = mrtService.getAllMRTStationsAsDTO();
        return ResponseEntity.ok(allMRTs);
    }

    @PostMapping("/nearest/{no}")
    public ResponseEntity<List<MRTDTO>> getNearestMRTs(@RequestBody UserLocationDTO userLocation, @PathVariable("no") int noOfStations) {
        List<MRTDTO> nearestMRTs = mrtService.findNearestMRTs(userLocation, noOfStations);
        return ResponseEntity.ok(nearestMRTs);
    }

}
