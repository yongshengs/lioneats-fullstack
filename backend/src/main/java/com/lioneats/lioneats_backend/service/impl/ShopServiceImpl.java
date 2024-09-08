package com.lioneats.lioneats_backend.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.lioneats.lioneats_backend.dto.SearchRequestDTO;
import com.lioneats.lioneats_backend.dto.google.ShopDTO;
import com.lioneats.lioneats_backend.mapper.google.ShopMapper;
import com.lioneats.lioneats_backend.model.Allergy;
import com.lioneats.lioneats_backend.model.Circle;
import com.lioneats.lioneats_backend.model.DishDetail;
import com.lioneats.lioneats_backend.model.Shop;
import com.lioneats.lioneats_backend.repository.AllergyRepository;
import com.lioneats.lioneats_backend.repository.CircleRepository;
import com.lioneats.lioneats_backend.repository.DishDetailRepository;
import com.lioneats.lioneats_backend.repository.ShopRepository;
import com.lioneats.lioneats_backend.service.ShopService;
import com.lioneats.lioneats_backend.util.UserShopGeoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    private static final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    private final ShopRepository shopRepository;
    private final DishDetailRepository dishDetailRepository;
    private final CircleRepository circleRepository;
    private final AllergyRepository allergyRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository,
                           DishDetailRepository dishDetailRepository,
                           CircleRepository circleRepository,
                           AllergyRepository allergyRepository) {
        this.shopRepository = shopRepository;
        this.dishDetailRepository = dishDetailRepository;
        this.circleRepository = circleRepository;
        this.allergyRepository = allergyRepository;
    }

    @Override
    public List<ShopDTO> getShopsByCriteria(SearchRequestDTO requestDTO) {
        List<Shop> allShops = new ArrayList<>();
        Set<Long> processedCircleIds = new HashSet<>();

        if (requestDTO.getLocation() != null && !requestDTO.getLocation().isEmpty()) {
            for (String mrtName : requestDTO.getLocation()) {
                Optional<Circle> optionalCircle = findNearestCircleByLocationName(mrtName);

                if (optionalCircle.isPresent()) {
                    Circle nearestCircle = optionalCircle.get();

                    if (!processedCircleIds.contains(nearestCircle.getId())) {
                        processedCircleIds.add(nearestCircle.getId());

                        logger.info("Nearest circle found: {} with MRT Station: {}",
                                nearestCircle.getId(),
                                nearestCircle.getMrt().getName());

                        List<Shop> shops = shopRepository.findByCircleId(nearestCircle.getId());
                        allShops.addAll(shops);
                    } else {
                        logger.info("Circle {} has already been processed. Skipping retrieval.", nearestCircle.getId());
                    }
                } else {
                    logger.warn("No nearest circle found for location: {}", mrtName);
                }
            }
        } else {

            allShops = shopRepository.findAll();
            logger.info("No location filter provided. Retrieved all shops.");
        }

        logger.info("Number of shops found before filtering: {}", allShops.size());

        List<DishDetail> dishEntities = convertDishes(requestDTO.getDishes());
        List<Allergy> allergyEntities = convertAllergies(requestDTO.getAllergies());

        logger.info("Starting filtering process...");

        List<Shop> filteredShops = allShops.stream()
                .filter(shop -> filterByAllergies(shop, allergyEntities))
                .filter(shop -> filterByDishes(shop, dishEntities))
                .filter(shop -> filterByBudget(shop, requestDTO.getBudget()))
                .filter(shop -> filterByRating(shop, requestDTO.getMinRating()))
                .collect(Collectors.toList());

        Collections.shuffle(filteredShops);

        logger.info("Filtering process completed. Number of shops found after filtering: {}", filteredShops.size());

        return filteredShops.stream()
                .map(ShopMapper::toDTO)
                .collect(Collectors.toList());
    }



    private boolean filterByRating(Shop shop, double minRating) {
        return shop.getRating() >= minRating;
    }


    @Override
    public List<ShopDTO> getShopsByLocation(double lat, double lng) {
        Optional<Circle> nearestCircle = findNearestCircle(lat, lng);

        if (nearestCircle.isPresent()) {
            logger.info("Nearest circle found: {} with center at ({}, {})",
                    nearestCircle.get().getMrt().getName(),
                    nearestCircle.get().getLatitude(),
                    nearestCircle.get().getLongitude());

            List<Shop> shops = shopRepository.findByCircleId(nearestCircle.get().getId());
            logger.info("length {}", shops.size());
            return shops.stream()
                    .map(ShopMapper::toDTO)
                    .collect(Collectors.toList());
        } else {
            logger.warn("No circle found for the location: ({}, {})", lat, lng);
            return List.of();
        }
    }

    @Override
    public Optional<Circle> findNearestCircleByLocationName(String mrtName) {
        return circleRepository.findByMrtNameIgnoreCase(mrtName);
    }

    @Override
    public Optional<Circle> findNearestCircle(double lat, double lng) {
        List<Circle> circles = circleRepository.findAll();

        // Log all the circles with their respective latitudes and longitudes
        circles.forEach(circle -> logger.info("Circle ID: {}, MRT: {}, Lat: {}, Lng: {}",
                circle.getId(), circle.getMrt().getName(), circle.getLatitude(), circle.getLongitude()));

        Optional<Circle> nearestCircle = circles.stream()
                .min(Comparator.comparingDouble(circle ->
                        UserShopGeoUtil.calculateDistance(lat, lng, circle.getLatitude(), circle.getLongitude())));

        nearestCircle.ifPresent(circle -> logger.info("Nearest Circle ID: {}, MRT: {}, Lat: {}, Lng: {}",
                circle.getId(), circle.getMrt().getName(), circle.getLatitude(), circle.getLongitude()));

        return nearestCircle;
    }

    @Override
    public Optional<Shop> getShopById(Long id) {
        return shopRepository.findById(id);
    }

    // Private helper methods for filtering and conversion
    private boolean filterByBudget(Shop shop, String budget) {
        int priceLevel = shop.getPriceLevel();

        return switch (budget.toUpperCase()) {
            case "HIGH" -> priceLevel >= 0 && priceLevel <= 5;
            case "MEDIUM" -> priceLevel >= 0 && priceLevel <= 3;
            case "LOW" -> priceLevel >= 0 && priceLevel <= 1;
            default -> true;
        };
    }

    private List<Allergy> convertAllergies(List<String> allergyNames) {
        return allergyNames.stream()
                .map(allergyName -> allergyRepository.findByName(allergyName)
                        .orElseThrow(() -> new RuntimeException("Allergy not found: " + allergyName)))
                .collect(Collectors.toList());
    }

    private List<DishDetail> convertDishes(List<String> dishNames) {
        return dishNames.stream()
                .map(dishName -> dishDetailRepository.findByName(dishName)
                        .orElseThrow(() -> new RuntimeException("Dish not found: " + dishName)))
                .collect(Collectors.toList());
    }

    private boolean filterByDishes(Shop shop, List<DishDetail> userDishes) {
        if (userDishes == null || userDishes.isEmpty()) {
            return true;
        }

        List<String> dishNames = userDishes.stream()
                .map(DishDetail::getName)
                .toList();

        return dishNames.stream()
                .anyMatch(dishName -> shop.getKeyword().equalsIgnoreCase(dishName));
    }

    private boolean filterByAllergies(Shop shop, List<Allergy> userAllergies) {
        if (userAllergies == null || userAllergies.isEmpty()) {
            return true;
        }

        List<DishDetail> safeDishes = getSafeDishes(userAllergies);

        List<String> safeDishNames = safeDishes.stream()
                .map(DishDetail::getName)
                .collect(Collectors.toList());

        return safeDishNames.stream()
                .anyMatch(dishName -> shop.getKeyword().toLowerCase().equals(dishName.toLowerCase()));
    }

    public List<DishDetail> getSafeDishes(List<Allergy> userAllergies) {
        List<DishDetail> allDishes = dishDetailRepository.findAll();

        return allDishes.stream()
                .filter(dish -> dish.getAllergies().stream().noneMatch(userAllergies::contains))
                .collect(Collectors.toList());
    }
}

