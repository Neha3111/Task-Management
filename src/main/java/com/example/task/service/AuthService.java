package com.example.task.service;

import com.example.task.model.User;
import com.example.task.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepo = new UserRepository();
    private User loggedInUser;

    public boolean signup(String username, String password) {
        if (userRepo.findByUsername(username) != null) {
            return false; // already exists
        }
        return userRepo.save(new User(username, password));
    }

    public boolean login(String username, String password) {
        User u = userRepo.findByUsername(username);
        if (u != null && u.getPassword().equals(password)) {
            loggedInUser = u;
            return true;
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
