package com.example.task.service;

import com.example.task.model.User;
import com.example.task.repository.UserRepository;
import com.example.task.util.PasswordUtil;
import org.springframework.stereotype.Service;

@Service

public class AuthService {
    private final UserRepository userRepo = new UserRepository();
    private User loggedInUser;

    public boolean signup(String username, String password) {
        if (userRepo.findByUsername(username) != null) {
            return false; // already exists
        }
        // hash before saving
        String hashed = PasswordUtil.hashPassword(password);
        return userRepo.save(new User(username, hashed));
    }

    public boolean login(String username, String password) {
        User u = userRepo.findByUsername(username);
        if (u != null) {
            String hashedInput = PasswordUtil.hashPassword(password);
            if (u.getPassword().equals(hashedInput)) {
                loggedInUser = u;
                return true;
            }
        }
        return false;
    }

    public void logout() {
        loggedInUser = null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
