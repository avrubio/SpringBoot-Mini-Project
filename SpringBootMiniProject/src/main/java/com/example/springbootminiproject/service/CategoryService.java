package com.example.springbootminiproject.service;

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


}
