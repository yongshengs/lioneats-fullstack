package com.lioneats.lioneats_backend.mapper;

import com.lioneats.lioneats_backend.dto.*;
import com.lioneats.lioneats_backend.model.*;
import com.lioneats.lioneats_backend.service.AllergyService;
import com.lioneats.lioneats_backend.service.DishDetailService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final AllergyService allergyService;
    private final DishDetailService dishDetailService;

    public UserMapper(AllergyService allergyService, DishDetailService dishDetailService) {
        this.allergyService = allergyService;
        this.dishDetailService = dishDetailService;
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setAge(userDTO.getAge());
        user.setGender(User.Gender.valueOf(userDTO.getGender()));
        user.setCountry(userDTO.getCountry());
        user.setPreferredBudget(User.PreferredBudget.valueOf(userDTO.getPreferredBudget()));
        user.setLikesSpicy(userDTO.isLikesSpicy());

        List<Allergy> allergies = userDTO.getAllergies().stream()
                .map(allergyName -> allergyService.findByName(allergyName)
                        .orElseThrow(() -> new RuntimeException("Allergy not found: " + allergyName)))
                .collect(Collectors.toList());
        user.setAllergies(allergies);

        List<DishDetail> dishPreferences = userDTO.getDishPreferences().stream()
                .map(dishName -> dishDetailService.findByName(dishName)
                        .orElseThrow(() -> new RuntimeException("Dish not found: " + dishName)))
                .collect(Collectors.toList());
        user.setDishPreferences(dishPreferences);

        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setAge(user.getAge());
        userDTO.setGender(user.getGender().name());
        userDTO.setCountry(user.getCountry());
        userDTO.setPreferredBudget(user.getPreferredBudget().name());
        userDTO.setLikesSpicy(user.isLikesSpicy());

        List<String> allergies = user.getAllergies().stream()
                .map(Allergy::getName)
                .collect(Collectors.toList());
        userDTO.setAllergies(allergies);

        List<String> dishPreferences = user.getDishPreferences().stream()
                .map(DishDetail::getName)
                .collect(Collectors.toList());
        userDTO.setDishPreferences(dishPreferences);

        return userDTO;
    }
}