package com.example.springbootminiproject.model.response;

public class LoginResponse {
    private String jwt;
    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }

}
