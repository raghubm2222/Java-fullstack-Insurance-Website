package com.ilp.casestudy.controllers;

import java.util.HashMap;
import java.util.Map;

import com.ilp.casestudy.db.UsersDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ilp.casestudy.models.User;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private UsersDatabase usersDatabase;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> userData) {
        User user = usersDatabase.getUserByEmail(userData.get("email"));
        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Email not registered with us");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        if (!user.getPassword().equals(userData.get("password"))) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Incorrect password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return  ResponseEntity.ok().body(user.toMap());
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Map<String, String> userData) {
        Map<String, String> response = new HashMap<>();
        try {
            User user = usersDatabase.getUserByEmail(userData.get("email"));
            if (user != null) {
                response.put("error", "Email is already registered");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
            User newUser = userFromMap(userData);
            usersDatabase.addUser(newUser);
            return ResponseEntity.ok(newUser.toMap());
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    public User userFromMap(Map<String, String> userData) {
        String name = userData.get("name");
        String email = userData.get("email");
        String password = userData.get("password");
        String address = userData.get("address");
        String phone = userData.get("phone");
        String nominee = userData.get("nominee");
        String relationship = userData.get("relationship");
        return new User(usersDatabase.generateId(), name, email, password, address, phone, nominee, relationship);
    }
}
