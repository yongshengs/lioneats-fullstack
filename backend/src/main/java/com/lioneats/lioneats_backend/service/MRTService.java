package com.lioneats.lioneats_backend.service;

import com.lioneats.lioneats_backend.dto.MRTDTO;
import com.lioneats.lioneats_backend.dto.UserLocationDTO;
import com.lioneats.lioneats_backend.model.MRT;

import java.util.List;
import java.util.Optional;

public interface MRTService {

    List<MRT> getAllMRTStations();

    List<MRTDTO> getAllMRTStationsAsDTO();

    Optional<MRT> getMRTStationById(Long id);

    MRT getMRTStationByName(String name);

    MRT saveMRTStation(MRT mrt);

    void deleteMRTStationById(Long id);

    List<MRT> saveAllMRTStations(List<MRT> mrtStations);

    List<MRTDTO> findNearestMRTs(UserLocationDTO userLocation, int noOfMrts);


}

