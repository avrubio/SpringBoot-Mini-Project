package com.example.springbootminiproject.service;


import com.example.springbootminiproject.model.User;
import com.example.springbootminiproject.repository.ProductRepository;

import com.example.springbootminiproject.exception.InformationExistException;
import com.example.springbootminiproject.exception.InformationNotFoundException;
import com.example.springbootminiproject.model.Category;
import com.example.springbootminiproject.model.Product;
import com.example.springbootminiproject.repository.CategoryRepository;
import com.example.springbootminiproject.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
Logger logger= Logger.getLogger(CategoryService.class.getName());
    /**
     * Sets the category repository for this service.
     *
     * @param categoryRepository The CategoryRepository to set.
     */
    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Sets the product repository for this service.
     *
     * @param productRepository The ProductRepository to set.
     */
    @Autowired // To connect to recipe repository and to use its methods
    public void setRecipeRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Get the currently logged-in user.
     *
     * @return The currently logged-in user.
     */
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    /**
     * Get all categories for the current logged-in user.
     *
     * @return List of categories.
     * @throws InformationNotFoundException if no categories are found for the user.
     */
    public List<Category> getCategories() {
        List<Category> categoryList = categoryRepository.findByUserId(CategoryService.getCurrentLoggedInUser().getId());
        if (categoryList.isEmpty()) {
            throw new InformationNotFoundException("no categories found for user id " + CategoryService.getCurrentLoggedInUser().getId());
        } else {
            return categoryList;
        }
    }

    /**
     * Get a category by its ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return Optional containing the category, or empty if not found.
     * @throws InformationNotFoundException if the category with the given ID is not found.
     */
    public Optional<Category> getCategory(Long categoryId) {
        Optional<Category> categoryOptional = Optional.of(categoryRepository.findByIdAndUserId(categoryId, CategoryService.getCurrentLoggedInUser().getId()));
        if (categoryOptional.isPresent()) {
            return categoryOptional;
        }
        throw new InformationNotFoundException("Category with Id " + categoryId + " not found");
    }

    /**
     * Create a new category.
     *
     * @param categoryObject The category object to create.
     * @return The created category.
     * @throws InformationExistException if a category with the same name already exists.
     */
    public Category createCategory(Category categoryObject) {
        Category category = categoryRepository.findByName(categoryObject.getName()).orElse(null);
        if (category != null) {
            throw new InformationExistException("category with name " + categoryObject.getName() + " already exists");
        } else {
            category.setUser(getCurrentLoggedInUser());
            return categoryRepository.save(categoryObject);
        }
    }

    /**
     * Update a category by its ID.
     *
     * @param categoryId The ID of the category to update.
     * @param category   The updated category data.
     * @return The updated category.
     * @throws InformationNotFoundException if the category with the given ID is not found.
     * @throws InformationExistException   if the category name and description remain unchanged.
     */
    public Category updateCategory(Long categoryId, Category category) {
        Category foundCategory = getCurrentLoggedInUser().findCategoryById(categoryId);
        if (foundCategory != null) {
            if (foundCategory.getName().equals(category.getName()) &&
                    foundCategory.getDescription().equals(category.getDescription())) {
                throw new InformationExistException("The category name is already " + foundCategory.getName() + " and description is already " + category.getDescription());
            } else {
                foundCategory.setId(categoryId);
                return categoryRepository.save(foundCategory);
            }
        } else {
            throw new InformationNotFoundException("Category with id " + categoryId + " not found.");
        }
    }

    /**
     * Delete a category by its ID.
     *
     * @param categoryId The ID of the category to delete.
     * @return The deleted category.
     * @throws InformationNotFoundException if the category with the given ID is not found.
     */
    public Category deleteCategory(Long categoryId) {
        Category foundCategory = getCurrentLoggedInUser().findCategoryById(categoryId);
        if (foundCategory != null) {
            categoryRepository.deleteById(categoryId);
            return foundCategory;
        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }

    /**
     * Create a new product within a category.
     *
     * @param categoryId     The ID of the category to add the product to.
     * @param productObject The product object to create.
     * @return The created product.
     * @throws InformationNotFoundException if the category with the given ID is not found.
     */
    public Product createCategoryProduct(Long categoryId, Product productObject) {
        try {
            Category foundCategory = getCurrentLoggedInUser().findCategoryById(categoryId);
            productObject.setCategory(foundCategory);
            return productRepository.save(productObject);
        } catch (NoSuchElementException e) {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }

    /**
     * Get all products within a category.
     *
     * @param categoryId The ID of the category to retrieve products from.
     * @return List of products in the category.
     * @throws InformationNotFoundException if the category with the given ID is not found.
     */
    public List<Product> getCategoriesProducts(Long categoryId) {
        try {
            Category foundCategory = getCurrentLoggedInUser().findCategoryById(categoryId);

            if (foundCategory != null) {
                Category category = (foundCategory);
                return category.getProductList(); // Assuming you have a getProducts() method in your Category class
            } else {
                throw new InformationNotFoundException("Category with id " + categoryId + " not found");
            }
        } catch (NoSuchElementException e) {
            throw new InformationNotFoundException("Category with id " + categoryId + " not found");
        }
    }

    /**
     * Get a specific product within a category.
     *
     * @param categoryId The ID of the category containing the product.
     * @param productId  The ID of the product to retrieve.
     * @return The requested product.
     * @throws InformationNotFoundException if the category or product with the given IDs is not found.
     */
    public Product getCategoryProduct(Long categoryId, Long productId) {
        Category foundCategory = getCurrentLoggedInUser().findCategoryById(categoryId);

        Optional<Product> productOptional = productRepository.findById(productId);

        if (foundCategory != null) {

            if (productOptional.isPresent()) {
                List<Product> product1 = foundCategory.getProductList().stream()
                        .filter(product -> product.getId().equals(productId))
                        .collect(Collectors.toList());
                if (product1.isEmpty()) {
                    throw new InformationNotFoundException("product with id " + productId + " not found in category with id " + categoryId);
                }
                return product1.get(0);
            } else {
                throw new InformationNotFoundException("product with id " + productId + " not found product");
            }
        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }

    /**
     * Update a product within a category.
     *
     * @param categoryId     The ID of the category containing the product.
     * @param productId      The ID of the product to update.
     * @param productObject  The updated product data.
     * @return The updated product.
     * @throws InformationNotFoundException if the category or product with the given IDs is not found.
     */
    public Product updateCategoryProduct(Long categoryId, Long productId, Product productObject) {
        Category foundCategory = getCurrentLoggedInUser().findCategoryById(categoryId);

        if (foundCategory != null) {
            try {
                Product product = foundCategory.getProductList().stream()
                        .filter(r -> r.getId().equals(productId))
                        .findFirst()
                        .get();
                product.setName(productObject.getName());
                product.setDescription(productObject.getDescription());

                return productRepository.save(product);
            } catch (NoSuchElementException e) {
                throw new InformationNotFoundException("Product with id " + productId + " not found");
            }
        } else {
            throw new InformationNotFoundException("Category with id " + categoryId + " not found");
        }
    }

    /**
     * Delete a product within a category.
     *
     * @param categoryId The ID of the category containing the product.
     * @param productId  The ID of the product to delete.
     * @return Optional containing the deleted product, or empty if not found.
     * @throws InformationNotFoundException if the category or product with the given IDs is not found.
     */
    public Optional<Product> deleteCategoryProduct(Long categoryId, Long productId) {
        Category foundCategory = getCurrentLoggedInUser().findCategoryById(categoryId);

        try {
            List<Product> product1 = foundCategory.getProductList().stream()
                    .filter(product -> product.getId().equals(productId))
                    .collect(Collectors.toList());
            productRepository.deleteById(product1.get(0).getId());
            return Optional.of(product1.get(0));
        } catch (NoSuchElementException e) {
            throw new InformationNotFoundException("Category or product not found.");
        }
    }

        }

