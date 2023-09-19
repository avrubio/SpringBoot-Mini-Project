package com.example.springbootminiproject.security;

import com.example.springbootminiproject.model.User;

public class MyUserDetails {
    private final User user;


    public MyUserDetails(User user) {
        this.user = user;
    }

}
