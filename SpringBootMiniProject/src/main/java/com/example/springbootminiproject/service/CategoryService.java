package com.example.springbootminiproject.service;

import com.example.springbootminiproject.expection.InformationExistException;
import com.example.springbootminiproject.expection.InformationNotFoundException;
import com.example.springbootminiproject.model.Category;
import com.example.springbootminiproject.model.User;
import com.example.springbootminiproject.repository.CategoryRepository;
import com.example.springbootminiproject.repository.ProductRepository;
import com.example.springbootminiproject.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired //to connect to recipe repository and to use its methods
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

}
