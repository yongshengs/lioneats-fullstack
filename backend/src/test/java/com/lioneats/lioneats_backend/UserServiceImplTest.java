package com.lioneats.lioneats_backend;

import com.lioneats.lioneats_backend.model.User;
import com.lioneats.lioneats_backend.repository.UserRepository;
import com.lioneats.lioneats_backend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {

        userRepository.deleteAll();
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@example.com");
        user.setName("Test User");
        user.setPreferredBudget(User.PreferredBudget.HIGH);

        User createdUser = userService.createUser(user);


        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        assertTrue(userService.checkPassword("password123", createdUser.getPassword()));
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setEmail("user1@example.com");
        user1.setName("TestUser1");
        user1.setPreferredBudget(User.PreferredBudget.HIGH);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password2");
        user2.setEmail("user2@example.com");
        user2.setName("TestUser2");
        user2.setPreferredBudget(User.PreferredBudget.HIGH);

        userService.createUser(user1);
        userService.createUser(user2);

        List<User> users = userService.getAllUsers();


        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@example.com");
        user.setName("TestUser1");
        user.setPreferredBudget(User.PreferredBudget.HIGH);

        //
        User savedUser = userService.createUser(user);
        Long userId = savedUser.getId();

        //
        Optional<User> foundUser = userService.getUserById(userId);

        //
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void testGetUserByUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@example.com");
        user.setName("TestUser1");
        user.setPreferredBudget(User.PreferredBudget.HIGH);


        userService.createUser(user);


        Optional<User> foundUser = userService.getUserByUsername("testuser");


        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void testGetUserByEmail() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@example.com");
        user.setName("TestUser1");
        user.setPreferredBudget(User.PreferredBudget.HIGH);


        userService.createUser(user);


        Optional<User> foundUser = userService.getUserByEmail("testuser@example.com");


        assertTrue(foundUser.isPresent());
        assertEquals("testuser@example.com", foundUser.get().getEmail());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@example.com");
        user.setName("TestUser1");
        user.setPreferredBudget(User.PreferredBudget.HIGH);

        //
        User savedUser = userService.createUser(user);

        //
        userService.deleteUser(savedUser.getId());

        //
        Optional<User> deletedUser = userService.getUserById(savedUser.getId());
        assertTrue(deletedUser.isEmpty());
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@example.com");
        user.setName("TestUser1");
        user.setPreferredBudget(User.PreferredBudget.HIGH);

        //
        User savedUser = userService.createUser(user);

        //
        savedUser.setUsername("updateduser");
        User updatedUser = userService.updateUser(savedUser);

        //
        assertEquals("updateduser", updatedUser.getUsername());
    }

    @Test
    void testCheckPassword() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@example.com");
        user.setName("TestUser1");
        user.setPreferredBudget(User.PreferredBudget.HIGH);

        //
        User createdUser = userService.createUser(user);

        //
        assertTrue(userService.checkPassword("password123", createdUser.getPassword()));
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@example.com");
        user.setName("TestUser1");
        user.setPreferredBudget(User.PreferredBudget.HIGH);

        //
        userService.createUser(user);

        //
        UserDetails userDetails = userService.loadUserByUsername("testuser");

        //
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        //
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("unknown");
        });
    }
}
