package com.example.springbootminiproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a user in the application.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {

    /**
     * The unique identifier of the user.
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user.
     */
    @Column
    private String userName;

    /**
     * The email address of the user (unique).
     */
    @Column(unique = true)
    private String emailAddress;

    /**
     * The password of the user (access set to WRITE_ONLY for security).
     */
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * The list of categories associated with this user.
     */
    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Category> categoryList;

    /**
     * The list of products associated with this user.
     */
    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> productList;

    /**
     * The user's profile information.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private UserProfile userProfile;

    /**
     * Find a category by its ID.
     *
     * @param id The ID of the category to find.
     * @return The found category or null if not found.
     */
    public Category findCategoryById(Long id) {
        return this.getCategoryList().stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
