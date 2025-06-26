package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.dto.UserActivityDto;
import com.example.fitnesstrackerapp.admin.dto.UserVitalDto;
import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class ActivityLogController {
    public void userActivityLog(UserActivityDto userActivityDto ,  Label lbl_sibmssionstatus) {
        UserService userService = new UserService();
        userService.addUserActivity( userActivityDto, lbl_sibmssionstatus);
    }
}
