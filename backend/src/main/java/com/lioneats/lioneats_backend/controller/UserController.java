package com.lioneats.lioneats_backend.controller;

import com.lioneats.lioneats_backend.dto.PasswordChangeDTO;
import com.lioneats.lioneats_backend.dto.UserDTO;
import com.lioneats.lioneats_backend.mapper.UserMapper;
import com.lioneats.lioneats_backend.model.User;
import com.lioneats.lioneats_backend.service.AllergyService;
import com.lioneats.lioneats_backend.service.DishDetailService;
import com.lioneats.lioneats_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;
    private final AllergyService allergyService;
    private final DishDetailService dishDetailService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, AllergyService allergyService, DishDetailService dishDetailService, UserMapper userMapper) {
        this.userService = userService;
        this.allergyService = allergyService;
        this.dishDetailService = dishDetailService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        if (userService.getUserByUsername(userDTO.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        if (userService.getUserByEmail(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        User user = userMapper.toEntity(userDTO);

        userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable("id") Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        
        if (userOptional.isPresent()) {
            UserDTO userDTO = userMapper.toDTO(userOptional.get());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserProfile(@PathVariable("id") Long id, @Valid @RequestBody UserDTO userDTO) {
        Optional<User> userOptional = userService.getUserById(id);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            User updatedUser = userMapper.toEntity(userDTO);
            updatedUser.setId(existingUser.getId());

            userService.createUser(updatedUser);

            UserDTO updatedUserDTO = userMapper.toDTO(updatedUser);
            return ResponseEntity.ok(updatedUserDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(
            @PathVariable("id") Long id,
            @Valid @RequestBody PasswordChangeDTO passwordChangeDTO) {

        Optional<User> userOptional = userService.getUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Check if old password matches
            if (!userService.checkPassword(passwordChangeDTO.getOldPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
            }

            // Check if new password and confirm password match
            if (!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getConfirmNewPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password and confirmation do not match");
            }

            // Update the password
            user.setPassword(passwordChangeDTO.getNewPassword());
            userService.createUser(user); // This will encode the new password and save the user

            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }



}

