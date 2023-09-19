package com.example.springbootminiproject.controller;

import com.example.springbootminiproject.model.User;
import com.example.springbootminiproject.model.request.LoginRequest;
import com.example.springbootminiproject.model.response.LoginResponse;
import com.example.springbootminiproject.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Controller class for user authentication.
 */
@RestController
@RequestMapping(path = "/auth/users/") // Base URL: http://localhost:9092/auth/users/
public class AuthController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register a new user.
     *
     * @param userObject The user object to register.
     * @return The registered user.
     */
    @PostMapping(path="/register/")
    public User createUser(@RequestBody User userObject){
        return userService.createUser(userObject);
    }

    /**
     * Authenticate a user and generate a JWT token upon successful login.
     *
     * @param loginRequest The login request containing user credentials.
     * @return A ResponseEntity containing the JWT token if authentication is successful,
     *         or an unauthorized status if authentication fails.
     */
    @PostMapping(path = "/login/") // Endpoint URL: http://localhost:9092/auth/users/login/
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<String> jwtToken = userService.loginUser(loginRequest);
        if (jwtToken.isPresent()) {
            return ResponseEntity.ok(new LoginResponse(jwtToken.get()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Authentication failed"));
        }
    }
}