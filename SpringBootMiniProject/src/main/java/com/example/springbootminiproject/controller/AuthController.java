package com.example.springbootminiproject.controller;

import com.example.springbootminiproject.model.User;
import com.example.springbootminiproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth/users/")//http://localhost:4444/auth/users/
public class AuthController {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @PostMapping(path="/register/")
    public User createUser(@RequestBody User userObject){
        return userService.createUser(userObject);
    }

}
