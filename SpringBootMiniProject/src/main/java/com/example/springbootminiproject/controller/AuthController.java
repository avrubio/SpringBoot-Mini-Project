package com.example.springbootminiproject.controller;

import com.example.springbootminiproject.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth/users/")//http://localhost:4444/auth/users/
public class AuthController {
    private UserService userService;

}
