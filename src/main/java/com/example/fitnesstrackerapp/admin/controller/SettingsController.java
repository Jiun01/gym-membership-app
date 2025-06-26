package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.dto.UserDto;
import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class SettingsController {
    public void updatdeUser(UserDto userDto, ActionEvent event, Label lbl_confirmchanges, String confirm_password) {
        UserService userService = new UserService();
        userService.updateUser(event, userDto, lbl_confirmchanges, confirm_password);

    }
}
