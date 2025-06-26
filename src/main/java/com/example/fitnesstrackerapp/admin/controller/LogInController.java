package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.dto.UserDto;
import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class LogInController {
    public void logInUser(UserDto userDto, ActionEvent event, Label lblgetstarted) {
        UserService userService = new UserService();
        userService.validateLogin(event, userDto.getEmail(), userDto.getPassword(), lblgetstarted);
    }
}
