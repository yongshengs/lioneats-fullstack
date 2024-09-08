package com.lioneats.lioneats_backend;

import com.lioneats.lioneats_backend.model.Dish;
import com.lioneats.lioneats_backend.model.DishDetail;
import com.lioneats.lioneats_backend.repository.DishDetailRepository;
import com.lioneats.lioneats_backend.repository.DishRepository;
import com.lioneats.lioneats_backend.service.DishService;
import com.lioneats.lioneats_backend.service.impl.DishDetailServiceImpl;
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
public class DishServiceImplTest {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishDetailServiceImpl dishDetailRepository;

    @Autowired
    private DishRepository DishRepo;

    @Autowired
    private DishDetailRepository DdRepo;
    @BeforeEach
    void setUp() {

        DishRepo.deleteAll();
    }

    @Test
    @Transactional
    void testCreateDish() {
        DishDetail dishDetail = new DishDetail();
        dishDetail.setName("Spaghetti Bolognese");
        dishDetail.setDescription("A classic Italian pasta dish.");

        Dish dish = new Dish();
        dish.setDishDetail(dishDetail);
        dish.setImageUrl("http://example.com/spaghetti.jpg");

        Dish createdDish = dishService.createDish(dish);

        assertNotNull(createdDish);
        assertEquals("Spaghetti Bolognese", createdDish.getDishDetail().getName());
        assertEquals("http://example.com/spaghetti.jpg", createdDish.getImageUrl());
    }



    @Test
    void testGetAllDishes() {
        DishDetail dishDetail1 = new DishDetail();
        dishDetail1.setName("Sushi");
        dishDetail1.setIsSpicy(false);
        dishDetail1.setIngredients("Rice, Vinegar, Fish");
        dishDetail1.setHistory("Sushi is a traditional Japanese dish.");
        dishDetail1.setDescription("Japanese dish with vinegared rice.");


        dishDetail1.setAllergies(new ArrayList<>());
        dishDetail1.setUsers(new ArrayList<>());
        dishDetail1.setFeedbacks(new ArrayList<>());

        DishDetail dishDetail2 = new DishDetail();
        dishDetail2.setName("Pizza");
        dishDetail2.setIsSpicy(false);
        dishDetail2.setIngredients("Flour, Tomato Sauce, Cheese");
        dishDetail2.setHistory("Pizza originated in Italy.");
        dishDetail2.setDescription("Italian dish with a flat round base of dough.");
        dishDetail2.setAllergies(new ArrayList<>());
        dishDetail2.setUsers(new ArrayList<>());
        dishDetail2.setFeedbacks(new ArrayList<>());


        dishDetailRepository.saveDishDetail(dishDetail1);
        dishDetailRepository.saveDishDetail(dishDetail2);

        Dish dish1 = new Dish();
        dish1.setDishDetail(dishDetail1);
        dish1.setImageUrl("http://example.com/sushi.jpg");

        Dish dish2 = new Dish();
        dish2.setDishDetail(dishDetail2);
        dish2.setImageUrl("http://example.com/pizza.jpg");

        dishService.createAllDishes(Arrays.asList(dish1, dish2));

        List<Dish> dishes = dishService.getAllDishes();

        assertNotNull(dishes);
        assertTrue(dishes.size() >= 2);
    }

    @Test
    void testGetDishById() {
        DishDetail dishDetail = new DishDetail();
        dishDetail.setName("Burger");
        dishDetail.setDescription("A sandwich consisting of one or more cooked patties of ground meat.");
        dishDetail.setIsSpicy(false);
        dishDetail.setIngredients("Flour, Tomato Sauce, Cheese");
        dishDetail.setHistory("Tradtional Hamburger");
        dishDetail.setAllergies(new ArrayList<>());
        dishDetail.setUsers(new ArrayList<>());
        dishDetail.setFeedbacks(new ArrayList<>());

        DishDetail savedDishDetail = DdRepo.save(dishDetail);
        Dish dish = new Dish();
        dish.setDishDetail(dishDetail);
        dish.setImageUrl("http://example.com/burger.jpg");

        Dish createdDish = dishService.createDish(dish);

        Optional<Dish> foundDish = dishService.getDishById(createdDish.getId());

        assertTrue(foundDish.isPresent());
        assertEquals("Burger", foundDish.get().getDishDetail().getName());
    }

    @Test
    void testDeleteDish() {
        DishDetail dishDetail = new DishDetail();
        dishDetail.setName("Pasta");
        dishDetail.setDescription("An Italian type of food typically made from an unleavened dough of wheat flour mixed with water.");
        dishDetail.setIsSpicy(false);
        dishDetail.setIngredients("Flour, Tomato Sauce, Cheese");
        dishDetail.setHistory("Pasta originated in Italy.");
        dishDetail.setAllergies(new ArrayList<>());
        dishDetail.setUsers(new ArrayList<>());
        dishDetail.setFeedbacks(new ArrayList<>());

        DishDetail savedDishDetail = DdRepo.save(dishDetail);
        Dish dish = new Dish();
        dish.setDishDetail(dishDetail);
        dish.setImageUrl("http://example.com/pasta.jpg");

        Dish createdDish = dishService.createDish(dish);
        int createdDishId = createdDish.getId();

        dishService.deleteDish(createdDishId);

        Optional<Dish> deletedDish = dishService.getDishById(createdDishId);

        assertFalse(deletedDish.isPresent());
    }
}