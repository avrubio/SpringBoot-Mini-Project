package com.example.springbootminiproject.security;

import com.example.springbootminiproject.service.MyUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class JwtRequestFilter {
    Logger logger = Logger.getLogger(JwtRequestFilter.class.getName());


    private MyUserDetailsService myUserDetailsService;
    private JWTUtils jwtUtils;

}
