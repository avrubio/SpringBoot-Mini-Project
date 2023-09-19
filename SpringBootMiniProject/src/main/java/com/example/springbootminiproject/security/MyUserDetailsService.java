package com.example.springbootminiproject.security;

import com.example.springbootminiproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService {
    private UserService userService;

}
