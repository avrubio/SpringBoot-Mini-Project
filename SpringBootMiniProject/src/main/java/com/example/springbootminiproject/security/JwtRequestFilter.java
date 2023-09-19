package com.example.springbootminiproject.security;

import com.example.springbootminiproject.service.MyUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.logging.Logger;

@Component
public class JwtRequestFilter {
    Logger logger = Logger.getLogger(JwtRequestFilter.class.getName());


    private MyUserDetailsService myUserDetailsService;
    private JWTUtils jwtUtils;

    @Autowired
    public void setMyUserDetailsService(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        // Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXJlc2gyQGdhLmNvbSIsImlhdCI6MTY5NDgwMDAzNiwiZXhwIjoxNjk0ODg2NDM2fQ.z3smvkvDJqOYz7699UjvH5JQ51MuWL-KXffegc1UxWU
        if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer")) {
            return headerAuth.substring(7);
        }
        logger.info("No header");
        return null;
    }

}
