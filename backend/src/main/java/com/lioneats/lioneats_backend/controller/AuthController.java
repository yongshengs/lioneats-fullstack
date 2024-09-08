package com.lioneats.lioneats_backend.controller;


import com.lioneats.lioneats_backend.dto.UserDTO;
import com.lioneats.lioneats_backend.model.User;
import com.lioneats.lioneats_backend.security.AuthenticationResponse;
import com.lioneats.lioneats_backend.security.JwtUtil;
import com.lioneats.lioneats_backend.service.AllergyService;
import com.lioneats.lioneats_backend.service.DishDetailService;
import com.lioneats.lioneats_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequestMapping("api/auth")
@RestController
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO loginRequest) {
        Optional<User> user = userService.getUserByUsername(loginRequest.getUsername());

        if (user.isPresent() && userService.checkPassword(loginRequest.getPassword(), user.get().getPassword())) {
            String token = jwtUtil.generateToken(user.get().getUsername());

            AuthenticationResponse response = new AuthenticationResponse(
                    token,
                    user.get().getId(),
                    user.get().getUsername(),
                    user.get().getEmail(),
                    "Login successful"
            );

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }



    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok("Logged out successfully");
    }
}


