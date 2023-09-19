package com.example.springbootminiproject.controller;

import com.example.springbootminiproject.service.CategoryService;
import com.example.springbootminiproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing users.
 */

@RestController
@RequestMapping(path="/api/users")
public class UserController {
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public UserController(UserService userService, CategoryService categoryService){
        this.userService = userService;
        this.categoryService = categoryService;
    }



}
