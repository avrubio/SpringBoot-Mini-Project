package com.example.springbootminiproject.service;

import com.example.springbootminiproject.expection.InformationExistException;
import com.example.springbootminiproject.expection.InformationNotFoundException;
import com.example.springbootminiproject.model.Category;
import com.example.springbootminiproject.model.Product;
import com.example.springbootminiproject.model.User;
import com.example.springbootminiproject.repository.CategoryRepository;
import com.example.springbootminiproject.repository.ProductRepository;
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

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired //to connect to product
    // repository and to use its methods
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static User getCurrentLoggedInUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    public List<Category> getCategories() {
        List<Category> categoryList = categoryRepository.findByUserId(CategoryService.getCurrentLoggedInUser().getId());
        if(categoryList.isEmpty()){
            throw new InformationNotFoundException("no categories found for user id " + CategoryService.getCurrentLoggedInUser().getId());
        } else {
            return categoryList;
        }
    }

    public Optional<Category> getCategory(Long categoryId){
        Optional<Category> categoryOptional = Optional.of(categoryRepository.findByIdAndUserId(categoryId, CategoryService.getCurrentLoggedInUser().getId()));
        if(categoryOptional.isPresent()){
            return categoryOptional
                    ;        }
        throw new InformationNotFoundException("Category with Id " + categoryId + " not found");
    }

    public Category createCategory(Category categoryObject) {
        Category category = categoryRepository.findByName(categoryObject.getName()).orElse(null);
        if (category != null) {
            throw new InformationExistException("category with name " + categoryObject.getName() + " already exists");
        } else {
            category.setUser(getCurrentLoggedInUser());
            return categoryRepository.save(categoryObject);
        }
    }

    public Category updateCategory(Long categoryId, Category category) {
        Category foundCategory = getCurrentLoggedInUser().findCategoryById(categoryId);
        if (foundCategory != null) {
            if (foundCategory.getName().equals(foundCategory.getName()) &&
                    foundCategory.getDescription().equals(foundCategory.getDescription())) {
                throw new InformationExistException("The category name is already " +foundCategory.getName() + " and description is already " + category.getDescription());
            } else {
                foundCategory.setId(categoryId);
                return categoryRepository.save(foundCategory);
            }
        } else {
            throw new InformationNotFoundException("Category with id " + categoryId + " not found.");
        }
    }
    public Category deleteCategory(Long categoryId) {
        Category foundCategory = getCurrentLoggedInUser().findCategoryById(categoryId);
        if (foundCategory != null) {
            categoryRepository.deleteById(categoryId);
            return foundCategory;
        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }

    public Product createCategoryProduct(Long categoryId, Product productObject) {
        try {
            Category foundCategory = getCurrentLoggedInUser().findCategoryById(categoryId);
            productObject.setCategory(foundCategory);
            return productRepository.save(productObject);
        } catch (NoSuchElementException e) {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }

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


    public Product getCategoryProduct(Long categoryId , Long productId){
        Category foundCategory = getCurrentLoggedInUser().findCategoryById(categoryId);

        Optional<Product> recipeOptional= productRepository.findById(productId);

        if(foundCategory != null ) {

            if(recipeOptional.isPresent()){
                List<Product> product1 = foundCategory.getProductList().stream().filter(product ->product.getId().equals(productId)).collect(Collectors.toList());
                if (product1.isEmpty()) {
                    throw new InformationNotFoundException("product with id " +productId + " not found in category with id " + categoryId);
                }
                return product1.get(0);
            } else {
                throw new InformationNotFoundException("product with id " +productId + " not found");
            }

        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }

    }

    public Product updateCategoryProduct(Long categoryId, Long productId, Product productObject) {
        Category foundCategory = getCurrentLoggedInUser().findCategoryById(categoryId);

        if (foundCategory != null) {
            try {
               Product product = foundCategory.getProductList().stream().filter(p -> p.getId().equals(productId)).findFirst().get();
                product.setName(productObject.getName());
                product.setDirections(productObject.getDirections());
                return productRepository.save(product);
            } catch (NoSuchElementException e) {
                throw new InformationNotFoundException("product with id " + productId + " not found");
            }
        } else {
            throw new InformationNotFoundException("Category with id " + categoryId + " not found");
        }
    }

    public Optional<Product> deleteCategoryProduct(Long categoryId, Long productId){
        Category foundCategory = getCurrentLoggedInUser().findCategoryById(categoryId);

        try{
            List<Product> product1 = foundCategory.getProductList().stream().filter(product -> product.getId().equals(productId)).collect(Collectors.toList());
            productRepository.deleteById(product1.get(0).getId());
            return Optional.of(product1.get(0));
        } catch(NoSuchElementException e){
            throw new InformationNotFoundException("Category or product not found.");
        }
    }


}
