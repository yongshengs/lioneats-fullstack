package com.lioneats.lioneats_backend.service.api;

import com.lioneats.lioneats_backend.model.Circle;
import com.lioneats.lioneats_backend.model.DishDetail;
import com.lioneats.lioneats_backend.repository.ShopRepository;
import com.lioneats.lioneats_backend.service.CircleMRTService;
import com.lioneats.lioneats_backend.service.DishDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

@Service
public class ShopDataInitializationService {

    private static final Logger log = LoggerFactory.getLogger(ShopDataInitializationService.class);

    private final ShopCachingService shopCachingService;
    private final DishDetailService dishDetailService;
    private final CircleMRTService circleMRTService;
    private final ShopRepository shopRepository;

    @Autowired
    public ShopDataInitializationService(ShopCachingService shopCachingService,
                                         DishDetailService dishDetailService,
                                         CircleMRTService circleMRTService,
                                         ShopRepository shopRepository) {
        this.shopCachingService = shopCachingService;
        this.dishDetailService = dishDetailService;
        this.circleMRTService = circleMRTService;
        this.shopRepository = shopRepository;
    }

    @Scheduled(cron = "0 0 0 1 */2 *")
    public void scheduleShopDataInitialization() {
        log.info("Scheduled shop data initialization started.");

        // Clear existing shop data
        deleteExistingShopData();

        // Initialize new shop data
        List<Circle> circles = circleMRTService.getAllCircles();
        initializeShopData(circles);
    }

    private void deleteExistingShopData() {
        log.info("Deleting existing shop data...");
        shopRepository.deleteAll();
        log.info("Existing shop data deleted.");
    }

    public void initializeShopData(List<Circle> circles) {
        LocalDateTime startTime = LocalDateTime.now();
        log.info("Shop data initialization started at: {}", startTime);

        List<DishDetail> dishes = dishDetailService.getAllDishDetails();

        log.info("Initializing shop data for {} circles and {} dishes", circles.size(), dishes.size());
        for (Circle circle : circles) {
            for (DishDetail dish : dishes) {
                log.info("Fetching and storing nearby shops for circle: {} and dish: {}", circle.getMrt().getName(), dish.getName());
                shopCachingService.fetchAndStoreNearbyShops(circle, dish);
            }
        }

        LocalDateTime endTime = LocalDateTime.now();  // Capture the end time
        log.info("Shop data initialization completed at: {}", endTime);
        log.info("Total time taken: {} seconds", java.time.Duration.between(startTime, endTime).getSeconds());
    }
}
