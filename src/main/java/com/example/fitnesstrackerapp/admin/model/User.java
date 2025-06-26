package com.example.fitnesstrackerapp.admin.model;


public class User {

    private Long userId;
    private String username;
    private String email;
    private String password;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public User(String name, String email) {
        this.username = name;
        this.email = email;
    }

    public User(Long userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(Long userId, String username, String phone, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "{ \"userId\": " + userId + ", \"username\": \"" + username + "\", \"phone\": \"" + phone +
                "\", \"email\": \"" + email + "\", \"password\": \"" + password + "\" }";

    }
}
