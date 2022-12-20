package com.ilp.casestudy.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ilp.casestudy.db.UsersService;
import com.ilp.casestudy.models.User;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> userData) {
        Map<String, String> response = new HashMap<>();
        try {
            User user = usersService.login(userData.get("email"), userData.get("password"));
            return ResponseEntity.ok().body(user.toMap());
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Map<String, String> userData) {
        Map<String, String> response = new HashMap<>();
        try {
            String id = usersService.generateId();
            String name = userData.get("name");
            String email = userData.get("email");
            String password = userData.get("password");
            String address = userData.get("address");
            String phone = userData.get("phone");
            String nominee = userData.get("nominee");
            String relationship = userData.get("relationship");

            User user = new User(id, name, email, password, address, phone, nominee, relationship);
            usersService.addUser(user);
            return ResponseEntity.ok(user.toMap());
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
