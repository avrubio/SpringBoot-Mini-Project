package com.example.springbootminiproject.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String firstName;
    private String lastName;
    private String profileDescription;
}