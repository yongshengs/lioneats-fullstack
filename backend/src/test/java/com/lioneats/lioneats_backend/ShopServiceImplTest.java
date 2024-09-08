package com.lioneats.lioneats_backend;

import com.lioneats.lioneats_backend.dto.SearchRequestDTO;
import com.lioneats.lioneats_backend.dto.google.ShopDTO;
import com.lioneats.lioneats_backend.model.*;
import com.lioneats.lioneats_backend.model.google.OpeningHour;
import com.lioneats.lioneats_backend.model.google.Photo;
import com.lioneats.lioneats_backend.model.google.Review;
import com.lioneats.lioneats_backend.repository.*;
import com.lioneats.lioneats_backend.service.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ShopServiceImplTest {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private DishDetailRepository dishDetailRepository;

    @Autowired
    private CircleRepository circleRepository;

    @Autowired
    private AllergyRepository allergyRepository;

    @Autowired
    private MRTRepository MRTrepo;

    @Autowired
    private DishRepository DishRepo;

    @BeforeEach
    void setUp() {
        shopRepository.deleteAll();
        DishRepo.deleteAll();
        dishDetailRepository.deleteAll();
        circleRepository.deleteAll();
        allergyRepository.deleteAll();

        MRT mrt = new MRT();
        mrt.setName("Station1");
        mrt.setLatitude(1.3000);
        mrt.setLongitude(103.8000);
        mrt.setLines(new ArrayList<>());

        MRT mrt2 = new MRT();
        mrt2.setName("Station2");
        mrt2.setLatitude(1.6000);
        mrt2.setLongitude(106.8000);
        mrt2.setLines(new ArrayList<>());

        MRTrepo.save(mrt);
        MRTrepo.save(mrt2);
        Circle circle = new Circle();
        circle.setMrt(mrt);
        circle.setShops(new ArrayList<>());
        circle.setRadius(2000);




        Circle circle2 = new Circle();
        circle2.setMrt(mrt2);
        circle2.setShops(new ArrayList<>());
        circle2.setRadius(2500);

        circleRepository.save(circle);
        circleRepository.save(circle2);

        Shop shop1 = new Shop();
        shop1.setPlaceId("placeId123");
        shop1.setName("SHOP1");
        shop1.setFormattedAddress("123 Main St, Cityville");
        shop1.setFormattedPhoneNumber("+1234567890");
        shop1.setRating(4.5);
        shop1.setPriceLevel(3);
        shop1.setWebsiteUrl("http://example.com");
        shop1.setGoogleUrl("http://maps.google.com/?q=placeId123");
        shop1.setUserRatingsTotal(150);
        shop1.setLatitude(1.3521);
        shop1.setLongitude(103.8198);
        shop1.setKeyword("Pizza");
        shop1.setCircle(circle);


        Photo photo = new Photo();
        photo.setImageUrl("http://example.com/photo.jpg");
        photo.setShop(shop1);

        Review review = new Review();
        review.setAuthorName("John Doe");
        review.setRating(5);
        review.setText("Great place!");
        review.setShop(shop1);

        OpeningHour openingHour = new OpeningHour();
        openingHour.setShop(shop1);
        openingHour.setWeekdayText(new ArrayList<>());
        openingHour.setShop(shop1);

        shop1.setPhotos(Arrays.asList(photo));
        shop1.setReviews(Arrays.asList(review));
        shop1.setOpeningHours(Arrays.asList(openingHour));

        shopRepository.save(shop1);

        Shop shop2 = new Shop();
        shop2.setPlaceId("placeId456");
        shop2.setName("SHOP2");
        shop2.setFormattedAddress("456 Market St, Townsville");
        shop2.setFormattedPhoneNumber("+0987654321");
        shop2.setRating(4.0);
        shop2.setPriceLevel(2);
        shop2.setWebsiteUrl("http://shop2.com");
        shop2.setGoogleUrl("http://maps.google.com/?q=placeId456");
        shop2.setUserRatingsTotal(200);
        shop2.setLatitude(1.3550);
        shop2.setLongitude(103.8250);
        shop2.setKeyword("Burger");
        shop2.setCircle(circle2);


        Photo photo2 = new Photo();
        photo2.setImageUrl("http://shop2.com/photo.jpg");
        photo2.setShop(shop2);

        Review review2 = new Review();
        review2.setAuthorName("Jane Smith");
        review2.setRating(4);
        review2.setText("Good burgers!");
        review2.setShop(shop2);

        OpeningHour openingHour2 = new OpeningHour();
        openingHour2.setShop(shop2);
        openingHour2.setWeekdayText(new ArrayList<>());
        openingHour2.setShop(shop2);

        shop2.setPhotos(Arrays.asList(photo2));
        shop2.setReviews(Arrays.asList(review2));
        shop2.setOpeningHours(Arrays.asList(openingHour2));

        shopRepository.save(shop2);


        DishDetail dish1 = new DishDetail();
        dish1.setName("Pizza");
        dish1.setIsSpicy(false);
        dish1.setIngredients("Rice, Vinegar, Fish");
        dish1.setHistory("Sushi is a traditional Japanese dish.");
        dish1.setDescription("Japanese dish with vinegared rice.");
        dish1.setAllergies(new ArrayList<>());
        dish1.setUsers(new ArrayList<>());
        dish1.setFeedbacks(new ArrayList<>());
        dishDetailRepository.save(dish1);


        DishDetail dish2 = new DishDetail();
        dish2.setName("Burger");
        dish2.setIsSpicy(false);
        dish2.setIngredients("Meat,Bread");
        dish2.setHistory("Burger is a traditioanl Western Food");
        dish2.setDescription("Burger we like to eat from Germany");
        dish2.setAllergies(new ArrayList<>());
        dish2.setUsers(new ArrayList<>());
        dish2.setFeedbacks(new ArrayList<>());
        dishDetailRepository.save(dish2);


        Allergy allergy = new Allergy();
        allergy.setName("Peanut");
        allergyRepository.save(allergy);
    }

    @Transactional
    @Test
    void testGetShopsByLocation() {
        double latitude = 1.3000;
        double longitude = 103.8000;
        List<ShopDTO> shops = shopService.getShopsByLocation(latitude, longitude);

        assertNotNull(shops);
        assertEquals(1, shops.size());
    }

    @Transactional
    @Test
    void testGetShopsByCriteria() {
        SearchRequestDTO requestDTO = new SearchRequestDTO();

        requestDTO.setLocation(Arrays.asList("Station1"));
        requestDTO.setBudget("MEDIUM");
        requestDTO.setAllergies(Arrays.asList("Peanut"));
        requestDTO.setDishes(new ArrayList<>());
        requestDTO.setMinRating(4.0);


        List<ShopDTO> shops = shopService.getShopsByCriteria(requestDTO);



        assertNotNull(shops);
        assertFalse(shops.isEmpty());
    }

    @Test
    void testGetShopById() {
        List<Shop> allShops = shopRepository.findAll();
        Shop firstShop = allShops.get(0);

        Optional<Shop> shop = shopService.getShopById(firstShop.getId());

        assertTrue(shop.isPresent());
        assertEquals(firstShop.getKeyword(), shop.get().getKeyword());
    }

    @Test
    void testSaveAndDeleteShop() {
        Circle circle = circleRepository.findAll().get(0);
        Shop newShop = new Shop();
        newShop.setCircle(circle);
        newShop.setKeyword("Sushi");
        newShop.setPriceLevel(4);

        Shop savedShop = shopRepository.save(newShop);
        assertNotNull(savedShop);
        assertEquals("Sushi", savedShop.getKeyword());

        shopRepository.deleteById(savedShop.getId());
        Optional<Shop> deletedShop = shopService.getShopById(savedShop.getId());
        assertFalse(deletedShop.isPresent());
    }
}
