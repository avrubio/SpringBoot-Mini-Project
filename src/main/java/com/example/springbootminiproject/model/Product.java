package com.example.springbootminiproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * Represents a product in the application.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class Product {

    /**
     * The unique identifier of the product.
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the product.
     */
    @Column
    private String name;

    /**
     * The description of the product.
     */
    @Column
    private String description;

    /**
     * The category to which this product belongs.
     */
    @JsonIgnore
    @ManyToOne // Many products belong to one category.
    @JoinColumn(name = "category_id")  // Foreign key referencing the category.
    private Category category;

    /**
     * The user who owns or manages this product.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    /**
     * Updates the product with new information.
     *
     * @param productObject The product object with updated data.
     * @return The updated product.
     */
    public Product update(Product productObject) {
        this.setName(productObject.getName());
        this.setDescription(productObject.getDescription());

        return this;
    }
}
