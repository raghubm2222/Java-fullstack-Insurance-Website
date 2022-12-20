package com.ilp.casestudy.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ilp.casestudy.models.User;

@Service
public class UsersService {
    private List<User> users;

    public UsersService() {
        users = new ArrayList<>();
    }

    public void addUser(User user) throws Exception {
        User user2 = getUserbyEmail(user.getEmail());
        if (user2 != null) {
            throw new Exception("User already exists");
        }
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public String generateId() {
        return "CUST" + String.format("%06d", users.size() + 1);
    }

    public User getUserbyEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    public User login(String email, String password) throws Exception {
        User user = getUserbyEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new Exception("Invalid password");
        }
        return user;
    }

    public User getUserbyId(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }
}
