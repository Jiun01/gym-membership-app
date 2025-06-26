package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.dto.UserVitalDto;
import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class VitalsController {
    public void userVital(UserVitalDto userVitalDto, ActionEvent event, Label lbl_added){
        UserService userService = new UserService();
        userService.addUserVitals(event, userVitalDto, lbl_added);

    }
}
