package com.example.springbootminiproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * Represents a user's profile in the application.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "profiles")
public class UserProfile {

    /**
     * The unique identifier of the user's profile.
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The first name of the user.
     */
    @Column
    private String firstName;

    /**
     * The last name of the user.
     */
    @Column
    private String lastName;

    /**
     * The description of the user's profile.
     */
    @Column
    private String profileDescription;

    /**
     * The user associated with this profile.
     */
    @JsonIgnore
    @OneToOne(mappedBy = "userProfile")
    private User user;
}
