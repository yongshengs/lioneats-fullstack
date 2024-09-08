package com.lioneats.lioneats_backend.service;

import com.lioneats.lioneats_backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    void deleteUser(Long id);

    User updateUser(User user);

    boolean checkPassword(String rawPassword, String encodedPassword);
}
