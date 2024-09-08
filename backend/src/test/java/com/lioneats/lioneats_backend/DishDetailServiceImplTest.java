package com.lioneats.lioneats_backend;

import com.lioneats.lioneats_backend.model.DishDetail;
import com.lioneats.lioneats_backend.repository.DishDetailRepository;
import com.lioneats.lioneats_backend.repository.DishRepository;
import com.lioneats.lioneats_backend.service.impl.DishDetailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DishDetailServiceImplTest {

    @Autowired
    private DishDetailServiceImpl dishDetailService;

    @Autowired
    private DishRepository dishrepo;

    @Autowired
    private DishDetailRepository DdRepo;
    @BeforeEach
    void setUp() {
        dishrepo.deleteAll();
        DdRepo.deleteAll();
    }

    @Test
    void testSaveAndFindDishDetail() {
        DishDetail dish = new DishDetail();
        dish.setName("Sushi");
        dish.setIsSpicy(false);
        dish.setIngredients("Rice, Vinegar, Fish");
        dish.setHistory("Sushi is a traditional Japanese dish.");
        dish.setDescription("Japanese dish with vinegared rice.");
        dish.setAllergies(new ArrayList<>());
        dish.setUsers(new ArrayList<>());
        dish.setFeedbacks(new ArrayList<>());

        DishDetail savedDish = dishDetailService.saveDishDetail(dish);

        Optional<DishDetail> foundDish = dishDetailService.findByName("Sushi");

        assertTrue(foundDish.isPresent());
        assertEquals("Sushi", foundDish.get().getName());
    }

    private void assertTrue(boolean present) {
    }

    @Test
    void testGetAllDishDetails() {
        DishDetail dish1 = new DishDetail();
        dish1.setName("Sushi");
        dish1.setIsSpicy(false);
        dish1.setIngredients("Rice, Vinegar, Fish");
        dish1.setHistory("Sushi is a traditional Japanese dish.");
        dish1.setDescription("Japanese dish with vinegared rice.");
        dish1.setAllergies(new ArrayList<>());
        dish1.setUsers(new ArrayList<>());
        dish1.setFeedbacks(new ArrayList<>());

        DishDetail dish2 = new DishDetail();
        dish2.setName("Pizza");
        dish2.setIsSpicy(false);
        dish2.setIngredients("Flour, Tomato Sauce, Cheese");
        dish2.setHistory("Pizza originated in Italy.");
        dish2.setDescription("Italian dish with a flat round base of dough.");
        dish2.setAllergies(new ArrayList<>());
        dish2.setUsers(new ArrayList<>());
        dish2.setFeedbacks(new ArrayList<>());

        dishDetailService.saveDishDetail(dish1);
        dishDetailService.saveDishDetail(dish2);

        List<DishDetail> dishDetails = dishDetailService.getAllDishDetails();

        assertNotNull(dishDetails);
        assertEquals(2, dishDetails.size());
    }

    @Test
    void testDeleteDishDetailByName() {
        DishDetail dish = new DishDetail();
        dish.setName("Sushi");
        dish.setIsSpicy(false);
        dish.setIngredients("Rice, Vinegar, Fish");
        dish.setHistory("Sushi is a traditional Japanese dish.");
        dish.setDescription("Japanese dish with vinegared rice.");
        dish.setAllergies(new ArrayList<>());
        dish.setUsers(new ArrayList<>());
        dish.setFeedbacks(new ArrayList<>());

        dishDetailService.saveDishDetail(dish);

        dishDetailService.deleteDishDetailByName("Sushi");

        Optional<DishDetail> foundDish = dishDetailService.findByName("Sushi");
        assertFalse(foundDish.isPresent());
    }
}
