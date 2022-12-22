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
        User user2 = getUserByEmail(user.getEmail());
        if (user2 != null) {
            throw new Exception("Email is already registered");
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

    public User getUserByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    public User login(String email, String password) throws Exception {
        User user = getUserByEmail(email);
        if (user == null) {
            throw new Exception("Email not registered with us");
        }
        if (!user.getPassword().equals(password)) {
            throw new Exception("Invalid password");
        }
        return user;
    }

    public User getUserById(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }
}
