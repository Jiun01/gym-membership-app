package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.dto.UserDto;
import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Label lbl_prfname;
    @FXML
    private Label lbl_prfage;
    @FXML
    private Label lbl_prfheight;
    @FXML
    private Label lbl_prfweight;
    @FXML
    private Label lbl_prftrgtweight;
    @FXML
    private Button btn_settings;
    @FXML
    private Button btn_viewprofile;
    @FXML
    private Button btn_mainmenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_viewprofile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                UserDto userDto = new UserDto();
                userDto.getName();
                UserService userService = new UserService();
                userService.viewProfile(event);


            }
        });


        btn_settings.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event,"app-settings.fxml", "App Settings");
        });

        btn_mainmenu.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event,"", "Welcome to Fitness Tracker");
        });

    }


}
