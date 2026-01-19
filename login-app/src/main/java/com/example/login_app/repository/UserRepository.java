package com.example.login_app.repository;

import com.example.login_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsernameAndPassword(String username,String password);
    User findByUsername(String username);
}
