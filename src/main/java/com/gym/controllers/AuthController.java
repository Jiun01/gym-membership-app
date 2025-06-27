package com.gym.controllers;

import com.gym.models.User;
import com.gym.models.Member;
import com.gym.models.Admin;
import com.gym.models.Receptionist;
import java.util.HashMap;
import java.util.Map;

public class AuthController {
    private final Map<String, User> users = new HashMap<>();

    public AuthController() {
        users.put("member1", new Member("member1", "pass123", "John Doe", "john.doe@example.com", "123-456-7890"));
        users.put("admin1", new Admin("admin1", "adminpass", "S001", "Jane Smith"));
        users.put("recp1", new Receptionist("recp1", "recppass", "S002", "Peter Jones"));
    }

    public User login(String userId, String password) {
        User user = users.get(userId);
        if (user != null && user.verifyLogin(password)) {
            System.out.println("Login successful for user: " + userId);
            return user;
        }
        System.out.println("Login failed for user: " + userId);
        return null;
    }

    public Member register(String userId, String password, String name, String email, String phoneNumber) {
        if (users.containsKey(userId)) {
            System.out.println("Registration failed: User ID '" + userId + "' already exists.");
            return null;
        }
        Member newMember = new Member(userId, password, name, email, phoneNumber);
        users.put(userId, newMember);
        System.out.println("Registration successful for new member: " + userId);
        return newMember;
    }
}
