package com.example.springbootminiproject.seed;

import com.example.springbootminiproject.model.Category;
import com.example.springbootminiproject.model.Product;
import com.example.springbootminiproject.model.User;
import com.example.springbootminiproject.repository.CategoryRepository;
import com.example.springbootminiproject.repository.ProductRepository;
import com.example.springbootminiproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SeedData implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
@Autowired
    public SeedData(@Lazy PasswordEncoder passwordEncoder, UserRepository userRepository, ProductRepository productRepository, CategoryRepository categoryRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public void run(String... args) throws Exception{
User user = new User();
user.setUserName("Ari");
user.setEmailAddress("av@gmail.com");
user.setPassword(passwordEncoder.encode("ari12345"));

userRepository.save(user);

        Category category = new Category();
        category.setName("Morning Skincare");
        category.setDescription("Do this in the morning as soon as you wake up");
        category.setUser(user);
        categoryRepository.save(category);

        Product product1 = new Product();
        product1.setCategory(category);
        product1.setName("Snail Mucin");
        product1.setDescription("Some text");

        product1.setUser(user);
        productRepository.save(product1);
    }

}
