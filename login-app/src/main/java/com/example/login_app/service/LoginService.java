package com.example.login_app.service;

import com.example.login_app.entity.User;
import com.example.login_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    // LOGIN
    public boolean validate(String username, String password) {
        User user =
                userRepository.findByUsernameAndPassword(username, password);
        return user != null;
    }

    // REGISTER
    public boolean register(String username, String password) {

        User existingUser = userRepository.findByUsername(username);

        if (existingUser != null) {
            return false; // already exists
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        userRepository.save(user);
        return true;
    }
}
