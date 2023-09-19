package com.example.springbootminiproject.controller;

import com.example.springbootminiproject.model.Category;
import com.example.springbootminiproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api") // http://localhost:4444/api
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/categories/") // http://localhost:4444/api/categories/
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }
    @GetMapping(path = "/categories/{categoryId}/") // http://localhost:4444/api/categories/
    public Optional<Category> getCategory(@PathVariable(value = "categoryId") Long categoryId)  {return categoryService.getCategory(categoryId);
    }

    @PostMapping(path = "/categories/") // http://localhost:4444/api/categories/
    public Category createCategory(@RequestBody Category categoryObject) {
        return categoryService.createCategory(categoryObject);
    }

    @PutMapping(path = "/categories/{categoryId}/") //placeholder inside curly brackets
    public Category updateCategory(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Category category) {
        return categoryService.updateCategory(categoryId, category);
    }
}
