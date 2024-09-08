package com.lioneats.lioneats_backend;

import com.lioneats.lioneats_backend.dto.MRTDTO;
import com.lioneats.lioneats_backend.dto.UserLocationDTO;
import com.lioneats.lioneats_backend.model.MRT;
import com.lioneats.lioneats_backend.repository.MRTRepository;
import com.lioneats.lioneats_backend.service.MRTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MRTServiceImplTest {

    @Autowired
    private MRTService mrtService;

    @Autowired
    private MRTRepository MRTRepo;



    @BeforeEach
    void setUp() {
        MRTRepo.deleteAll();

        MRT mrt1 = new MRT();
        mrt1.setName("Station1");
        mrt1.setLatitude(1.3000);
        mrt1.setLongitude(103.8000);

        MRT mrt2 = new MRT();
        mrt2.setName("Station2");
        mrt2.setLatitude(1.3100);
        mrt2.setLongitude(103.8100);

        mrtService.saveMRTStation(mrt1);
        mrtService.saveMRTStation(mrt2);
    }

    @Test
    void testGetAllMRTStations() {
        List<MRT> mrtStations = mrtService.getAllMRTStations();
        assertNotNull(mrtStations);
        assertEquals(2, mrtStations.size());
    }

    @Test
    void testGetMRTStationById() {
        MRT station1 = new MRT();
        station1.setName("Station1");
        station1.setLatitude(1.3000);
        station1.setLongitude(103.8000);
        station1.setLines(new ArrayList<>());

        MRT savedStation = mrtService.saveMRTStation(station1);


        Optional<MRT> station = mrtService.getMRTStationById(savedStation.getId());


        assertTrue(station.isPresent());
        assertEquals("Station1", station.get().getName());
    }

    @Test
    void testGetMRTStationByName() {
        MRT station = mrtService.getMRTStationByName("Station2");
        assertNotNull(station);
        assertEquals("Station2", station.getName());
    }

    @Test
    void testFindNearestMRTs() {
        UserLocationDTO userLocation = new UserLocationDTO();
        userLocation.setLatitude(1.3050);
        userLocation.setLongitude(103.8050);

        List<MRTDTO> nearestMRTs = mrtService.findNearestMRTs(userLocation,2);
        assertNotNull(nearestMRTs);
        assertEquals(2, nearestMRTs.size());


        nearestMRTs.forEach(mrt ->
                System.out.println("Station: " + mrt.getName() + ", Distance: " + mrt.getDistance())
        );


        assertEquals("Station1", nearestMRTs.get(1).getName());
        assertEquals("Station2", nearestMRTs.get(0).getName());
    }

    @Test
    void testDeleteMRTStationById() {

        MRT station1 = new MRT();
        station1.setName("Station1");
        station1.setLatitude(1.3000);
        station1.setLongitude(103.8000);
        station1.setLines(new ArrayList<>());

        MRT savedStation = mrtService.saveMRTStation(station1);

        mrtService.deleteMRTStationById(savedStation.getId());


        Optional<MRT> station = mrtService.getMRTStationById(savedStation.getId());
        assertFalse(station.isPresent());
    }

    @Test
    void testSaveAllMRTStations() {
        MRT mrt3 = new MRT();
        mrt3.setName("Station3");
        mrt3.setLatitude(1.3200);
        mrt3.setLongitude(103.8200);

        List<MRT> stations = mrtService.saveAllMRTStations(List.of(mrt3));
        assertNotNull(stations);
        assertEquals(1, stations.size());
        assertEquals("Station3", stations.get(0).getName());
    }
}