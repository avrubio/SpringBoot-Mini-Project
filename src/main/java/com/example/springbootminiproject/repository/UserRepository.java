package com.example.springbootminiproject.repository;

import com.example.springbootminiproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //to register
    boolean existsByEmailAddress(String userEmailAddress);


    //to login
    User findUserByEmailAddress(String emailAddress);

}
