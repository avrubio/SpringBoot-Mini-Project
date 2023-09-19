package com.example.springbootminiproject.security;

import com.example.springbootminiproject.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

public class MyUserDetails {
    private final User user;


    public MyUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
