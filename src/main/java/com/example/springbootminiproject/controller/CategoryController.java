package com.example.springbootminiproject.controller;

import com.example.springbootminiproject.model.Category;
import com.example.springbootminiproject.model.Product;
import com.example.springbootminiproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for managing categories and products.
 */
@RestController
@RequestMapping("/api") // Base URL: http://localhost:4444/api
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Get all categories.
     *
     * @return A list of categories.
     */
    @GetMapping(path = "/categories/") // Endpoint URL: http://localhost:4444/api/categories/
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    /**
     * Get a specific category by ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return An Optional containing the category if found.
     */
    @GetMapping(path = "/categories/{categoryId}/") // Endpoint URL: http://localhost:4444/api/categories/{categoryId}/
    public Optional<Category> getCategory(@PathVariable(value = "categoryId") Long categoryId)  {
        return categoryService.getCategory(categoryId);
    }

    /**
     * Create a new category.
     *
     * @param categoryObject The category to create.
     * @return The created category.
     */
    @PostMapping(path = "/categories/") // Endpoint URL: http://localhost:4444/api/categories/
    public Category createCategory(@RequestBody Category categoryObject) {
        return categoryService.createCategory(categoryObject);
    }

    /**
     * Update an existing category.
     *
     * @param categoryId The ID of the category to update.
     * @param category The updated category object.
     * @return The updated category.
     */
    @PutMapping(path = "/categories/{categoryId}/") // Endpoint URL: http://localhost:4444/api/categories/{categoryId}/
    public Category updateCategory(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Category category) {
        return categoryService.updateCategory(categoryId, category);
    }

    /**
     * Delete a category by ID.
     *
     * @param categoryId The ID of the category to delete.
     * @return The deleted category.
     */
    @DeleteMapping(path = "/categories/{categoryId}/") // Endpoint URL: http://localhost:4444/api/categories/{categoryId}/
    public Category deleteCategory(@PathVariable(value = "categoryId") Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    // Products CRUD

    /**
     * Create a new product under a specific category.
     *
     * @param categoryId The ID of the category to which the product belongs.
     * @param productObject The product to create.
     * @return The created product.
     */
    @PostMapping(path="/categories/{categoryId}/products/")
    public Product createCategoryProduct(@PathVariable(value = "categoryId" ) Long categoryId, @RequestBody Product productObject) {
        return categoryService.createCategoryProduct(categoryId, productObject);
    }

    /**
     * Get all products associated with a specific category.
     *
     * @param categoryId The ID of the category.
     * @return A list of products in the category.
     */
    @GetMapping(path = "/categories/{categoryId}/products/") // Endpoint URL: http://localhost:4444/api/categories/{categoryId}/products/
    public List<Product> getCategoriesProducts(@PathVariable(value = "categoryId") Long categoryId) {
        return categoryService.getCategoriesProducts(categoryId);
    }

    /**
     * Get a specific product by ID within a category.
     *
     * @param categoryId The ID of the category.
     * @param productId The ID of the product to retrieve.
     * @return The requested product.
     */
    @GetMapping(path = "/categories/{categoryId}/products/{productId}/")
    public Product getCategoryProduct(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "productId") Long productId) {
        return categoryService.getCategoryProduct(categoryId, productId);
    }

    /**
     * Update a product within a category.
     *
     * @param categoryId The ID of the category.
     * @param productId The ID of the product to update.
     * @param productObject The updated product.
     * @return The updated product.
     */
    @PutMapping(path = "/categories/{categoryId}/products/{productId}/")
    public Product updateCategoryProduct(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "productId") Long productId, @RequestBody Product productObject) {
        return categoryService.updateCategoryProduct(categoryId, productId, productObject);
    }

    /**
     * Delete a product within a category by ID.
     *
     * @param categoryId The ID of the category.
     * @param productId The ID of the product to delete.
     * @return An Optional containing the deleted product, if found.
     */
    @DeleteMapping(path = "/categories/{categoryId}/products/{productId}/")
    public Optional<Product> deleteCategoryProduct(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "productId") Long productId) {
        return categoryService.deleteCategoryProduct(categoryId, productId);
    }
}