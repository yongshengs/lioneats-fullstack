package com.lioneats.lioneats_backend.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lioneats.lioneats_backend.dto.MRTDTO;
import com.lioneats.lioneats_backend.dto.UserLocationDTO;
import com.lioneats.lioneats_backend.mapper.MRTMapper;
import com.lioneats.lioneats_backend.model.MRT;
import com.lioneats.lioneats_backend.repository.MRTRepository;
import com.lioneats.lioneats_backend.service.MRTService;
import com.lioneats.lioneats_backend.util.UserShopGeoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MRTServiceImpl implements MRTService {

    private final MRTRepository mrtRepository;

    @Autowired
    public MRTServiceImpl(MRTRepository mrtRepository) {
        this.mrtRepository = mrtRepository;
    }

    @Override
    public List<MRT> getAllMRTStations() {
        return mrtRepository.findAll();
    }

    @Override
    public Optional<MRT> getMRTStationById(Long id) {
        return mrtRepository.findById(id);
    }

    @Override
    public MRT getMRTStationByName(String name) {
        return mrtRepository.findByName(name);
    }


    @Override
    public MRT saveMRTStation(MRT mrt) {
        return mrtRepository.save(mrt);
    }

    @Override
    public void deleteMRTStationById(Long id) {
        mrtRepository.deleteById(id);
    }

    @Override
    public List<MRT> saveAllMRTStations(List<MRT> mrtStations) {
        return mrtRepository.saveAll(mrtStations);
    }

    @Override
    public List<MRTDTO> findNearestMRTs(UserLocationDTO userLocation, int noOfMrts) {
        List<MRT> allMRTs = mrtRepository.findAll();

        return allMRTs.stream()
                .map(mrt -> {
                    double distanceInMeters = UserShopGeoUtil.calculateDistance(
                            userLocation.getLatitude(),
                            userLocation.getLongitude(),
                            mrt.getLatitude(),
                            mrt.getLongitude()
                    );
                    MRTDTO mrtDTO = MRTMapper.toDTO(mrt);
                    mrtDTO.setDistance(distanceInMeters);  // Set the distance in meters in MRTDTO
                    return mrtDTO;
                })
                .sorted(Comparator.comparingDouble(MRTDTO::getDistance))
                .limit(noOfMrts)
                .collect(Collectors.toList());
    }

    @Override
    public List<MRTDTO> getAllMRTStationsAsDTO() {
        return mrtRepository.findAll().stream()
                .map(MRTMapper::toDTO)
                .collect(Collectors.toList());
    }
}

