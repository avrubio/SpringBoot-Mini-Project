package com.example.springbootminiproject.service;

import com.example.springbootminiproject.exception.InformationExistException;
import com.example.springbootminiproject.model.User;
import com.example.springbootminiproject.model.request.LoginRequest;
import com.example.springbootminiproject.repository.UserRepository;
import com.example.springbootminiproject.security.JWTUtils;
import com.example.springbootminiproject.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructor for UserService.
     *
     * @param userRepository       The repository for user data.
     * @param passwordEncoder      The encoder for user passwords.
     * @param jwtUtils             The utility for JWT token generation.
     * @param authenticationManager The authentication manager for user login.
     */
    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder,
                       JWTUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Create a new user.
     *
     * @param userObject The user object to create.
     * @return The created user.
     * @throws InformationExistException if a user with the same email address already exists.
     */
    public User createUser(User userObject) {
        if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())) {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        } else {
            throw new InformationExistException("user with email address " + userObject.getEmailAddress() + " already exists");
        }
    }

    /**
     * Attempt to log in a user with the provided credentials.
     *
     * @param loginRequest The login request containing user credentials.
     * @return An optional JWT token if login is successful, empty otherwise.
     */
    public Optional<String> loginUser(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            return Optional.of(jwtUtils.generateJwtToken(myUserDetails));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    /**
     * Find a user by their email address.
     *
     * @param emailAddress The email address of the user to find.
     * @return The user object if found, or null if not found.
     */
    public User findUserByEmailAddress(String emailAddress) {
        return userRepository.findUserByEmailAddress(emailAddress);
    }


}