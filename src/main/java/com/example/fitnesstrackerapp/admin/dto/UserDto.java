package com.example.fitnesstrackerapp.admin.dto;


public class UserDto {

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


    public UserDto() {
    }


    public UserDto(String name, String email) {
        this.username = name;
        this.email = email;
    }

    public UserDto(Long userId, String username, String email, String password, String phone) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "{userId:" + userId + ", username:" + username + ", email:" + email + ", password:" + password + ", phone:" + phone + "}";

    }

}
