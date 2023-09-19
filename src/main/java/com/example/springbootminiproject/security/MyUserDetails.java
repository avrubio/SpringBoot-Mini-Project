package com.example.springbootminiproject.security;

import com.example.springbootminiproject.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * Custom UserDetails implementation for representing user details in Spring Security.
 */
public class MyUserDetails implements UserDetails {

    private final User user;

    /**
     * Constructs a MyUserDetails instance with the associated user.
     *
     * @param user The user for whom the user details are created.
     */
    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // In this example, we return an empty set of granted authorities.
        return new HashSet<>();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmailAddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Get the associated user.
     *
     * @return The associated User object.
     */
    public User getUser() {
        return user;
    }
}
