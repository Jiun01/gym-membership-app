package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.dto.UserDto;
import com.example.fitnesstrackerapp.admin.dto.UserRegistrationResponseDto;
import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class SignInController {
    public void signIn(UserDto userDto, ActionEvent event, Label lbl_work, String confirm_password) {
        UserService userService = new UserService();

        UserRegistrationResponseDto responseDto = userService.userSigning(event, userDto, lbl_work, confirm_password);
        if (responseDto != null && !responseDto.getUsers().isEmpty()) {
            userService.changeScence1(event, "user-login.fxml", "You can now Login to your account");
        } else {
            lbl_work.setText(responseDto.getResponseMessage());
        }
    }
}
