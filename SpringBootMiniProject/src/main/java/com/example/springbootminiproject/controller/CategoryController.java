package com.example.springbootminiproject.controller;

import com.example.springbootminiproject.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api") // http://localhost:4444/api
public class CategoryController {
    private CategoryService categoryService;


}
