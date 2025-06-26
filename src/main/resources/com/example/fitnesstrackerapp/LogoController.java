package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LogoController implements Initializable {
    @FXML
    private Button btn_getstarted;
    public void initialize(URL url, ResourceBundle resourceBundle){
        btn_getstarted.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event,"user-signin.fxml", "Welcome to Fitness Tracker App");
        });
    }

}
