package com.example.springbootminiproject.service;

import com.example.springbootminiproject.repository.CategoryRepository;
import com.example.springbootminiproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
