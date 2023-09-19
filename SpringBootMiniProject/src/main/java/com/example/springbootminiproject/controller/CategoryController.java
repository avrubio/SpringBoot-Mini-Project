package com.example.springbootminiproject.controller;

import com.example.springbootminiproject.model.Category;
import com.example.springbootminiproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api") // http://localhost:4444/api
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/categories/") // http://localhost:8080/api/categories/
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }


}
