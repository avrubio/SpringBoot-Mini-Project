package com.example.springbootminiproject.security;

import com.example.springbootminiproject.model.User;
import com.example.springbootminiproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom UserDetailsService implementation for loading user details from the database.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Load user details by email address.
     *
     * @param emailAddress The email address of the user to load.
     * @return UserDetails representing the loaded user.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user = userService.findUserByEmailAddress(emailAddress);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + emailAddress);
        }

        return new MyUserDetails(user);
    }
}
