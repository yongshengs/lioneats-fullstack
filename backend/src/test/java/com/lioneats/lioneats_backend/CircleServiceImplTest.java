package com.lioneats.lioneats_backend;

import com.lioneats.lioneats_backend.model.Circle;
import com.lioneats.lioneats_backend.model.MRT;
import com.lioneats.lioneats_backend.repository.CircleRepository;
import com.lioneats.lioneats_backend.repository.MRTRepository;
import com.lioneats.lioneats_backend.service.impl.CircleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CircleServiceImplTest {

    @Autowired
    private CircleServiceImpl circleService;

    @Autowired
    private CircleRepository circleRepository;

    @Autowired
    private MRTRepository mrtRepository;
    @BeforeEach
    void setUp() {
        circleRepository.deleteAll(); //
        mrtRepository.deleteAll();
    }

    @Test
    void testGetAllCircles() {
        MRT mrt1 = new MRT("MRT1", 1.3229625047963691, 103.87129771138326, Arrays.asList("Line1", "Line2"));
        MRT mrt2 = new MRT("MRT2", 1.3020976650090157, 103.8322946524372, Arrays.asList("Line3"));
        mrtRepository.saveAll(Arrays.asList(mrt1, mrt2));
        Circle circle1 = new Circle(mrt1, 100);
        Circle circle2 = new Circle(mrt2, 200);

        circleRepository.saveAll(List.of(circle1, circle2));

        List<Circle> result = circleService.getAllCircles();
        assertEquals(2, result.size());
        assertEquals(100, result.get(0).getRadius());
        assertEquals(200, result.get(1).getRadius());
    }

    @Test
    void testGetCircleById() {
        MRT mrt = new MRT("MRT1", 1.3229625047963691, 103.87129771138326, Arrays.asList("Line1", "Line2"));
        mrtRepository.save(mrt);
        Circle circle = new Circle(mrt, 100);
        Circle savedCircle = circleRepository.save(circle);
        mrtRepository.save(mrt);

        Optional<Circle> result = circleService.getCircleById(savedCircle.getId());
        assertTrue(result.isPresent());
        assertEquals(100, result.get().getRadius());
    }

    @Test
    void testSaveCircle() {
        MRT mrt = new MRT("MRT1", 1.3229625047963691, 103.87129771138326, Arrays.asList("Line1", "Line2"));
        mrtRepository.save(mrt);
        Circle circle = new Circle(mrt, 100);
        Circle result = circleService.saveCircle(circle);
        assertNotNull(result.getId());
        assertEquals(100, result.getRadius());
        assertEquals("MRT1", result.getMrt().getName());
        assertEquals(2, result.getMrt().getLines().size());
        assertEquals("Line1", result.getMrt().getLines().get(0));
    }

    @Test
    void testSaveAllCircles() {
        MRT mrt1 = new MRT("MRT1", 1.3229625047963691, 103.87129771138326, Arrays.asList("Line1", "Line2"));
        MRT mrt2 = new MRT("MRT2", 3.0, 4.0, Arrays.asList("Line3"));
        mrtRepository.saveAll(Arrays.asList(mrt1, mrt2));
        Circle circle1 = new Circle(mrt1, 100);
        Circle circle2 = new Circle(mrt2, 200);

        List<Circle> result = circleService.saveAllCircles(List.of(circle1, circle2));
        assertEquals(2, result.size());
        assertEquals(100, result.get(0).getRadius());
        assertEquals(200, result.get(1).getRadius());
        assertEquals("MRT2", result.get(1).getMrt().getName());
    }

    @Test
    void testDeleteCircleById() {
        MRT mrt = new MRT("MRT1", 1.3229625047963691, 103.87129771138326, Arrays.asList("Line1", "Line2"));
        mrtRepository.save(mrt);
        Circle circle = new Circle(mrt, 100);
        Circle savedCircle = circleRepository.save(circle);

        circleService.deleteCircleById(savedCircle.getId());

        Optional<Circle> result = circleRepository.findById(savedCircle.getId());
        assertFalse(result.isPresent());
    }
}