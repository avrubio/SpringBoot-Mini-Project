package com.example.springbootminiproject.service;

import com.example.springbootminiproject.model.User;
import com.example.springbootminiproject.security.MyUserDetails;
import com.example.springbootminiproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user = userService.findUserByEmailAddress(emailAddress);
        return new MyUserDetails(user);
    }
}
