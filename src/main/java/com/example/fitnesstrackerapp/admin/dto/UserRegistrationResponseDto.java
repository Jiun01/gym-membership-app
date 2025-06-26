package com.example.fitnesstrackerapp.admin.dto;


import com.example.fitnesstrackerapp.admin.model.User;

import java.util.List;

public class UserRegistrationResponseDto {

    List<User> users ;

    String responseMessage;

    public UserRegistrationResponseDto() {

    }

    public UserRegistrationResponseDto(List<User> users, String responseMessage) {
        this.users = users;
        this.responseMessage = responseMessage;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
