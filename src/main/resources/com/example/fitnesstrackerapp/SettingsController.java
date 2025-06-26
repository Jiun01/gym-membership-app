package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.dto.UserDto;
import com.example.fitnesstrackerapp.admin.service.UserService;
import com.example.fitnesstrackerapp.utils.PasswordHashing;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private TextField tf_changename;
    @FXML
    private TextField tf_changepass;
    @FXML
    private TextField tf_changeconfirmpass;
    @FXML
    private Button btn_submitchanges;
    @FXML
    private Button btn_returnprofile;
    @FXML
    private Label lbl_confirmchanges;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_submitchanges.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UserService userService = new UserService();
                String password = PasswordHashing.hashPassword(tf_changepass.getText());
                String confirm_password = PasswordHashing.hashPassword(tf_changeconfirmpass.getText());

                //after validate login create user
                UserDto userDto = new UserDto();
                userDto.setPassword(password);
                userDto.setName(tf_changename.getText());


                userService.addUser(event, userDto, lbl_confirmchanges, confirm_password);

            }
        });
        btn_returnprofile.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event,"profile-view.fxml", "User Profile");
        });


    }

}
