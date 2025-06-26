package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.dto.UserVitalDto;
import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class VitalsController implements Initializable {
    @FXML
    private TextField tf_age;
    @FXML
    private TextField tf_height;
    @FXML
    private TextField tf_currentweight;
    @FXML
    private TextField tf_targetweight;
    @FXML
    private Button btn_submit;
    @FXML
    private Label lbl_added;
    @FXML
    public Label lbl_email;
    @FXML
    public Label lbl_checker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Initialize an empty StringBuilder to collect missing fields
                StringBuilder missingFields = new StringBuilder("Details Incomplete! Please fill in");
                boolean isIncomplete = false;

                // Check if age is empty
                if (tf_age.getText().isEmpty()) {
                    missingFields.append(" age");
                    isIncomplete = true;
                }

                // Check if height is empty
                if (tf_height.getText().isEmpty()) {
                    if (isIncomplete) missingFields.append(" and");
                    missingFields.append(" height");
                    isIncomplete = true;
                }

                // Check if current weight is empty
                if (tf_currentweight.getText().isEmpty()) {
                    if (isIncomplete) missingFields.append(" and");
                    missingFields.append(" current weight");
                    isIncomplete = true;
                }

                // Check if target weight is empty
                if (tf_targetweight.getText().isEmpty()) {
                    if (isIncomplete) missingFields.append(" and");
                    missingFields.append(" target weight");
                    isIncomplete = true;
                }

                // If all fields are empty, show a general message
                if (tf_age.getText().isEmpty() && tf_height.getText().isEmpty() &&
                        tf_currentweight.getText().isEmpty() && tf_targetweight.getText().isEmpty()) {
                    lbl_checker.setText("Details Incomplete! Please fill in all details");
                    return;
                }

                // If specific fields are missing, show the constructed message
                if (isIncomplete) {
                    lbl_checker.setText(missingFields.toString());
                    return;
                }

                UserService userService = new UserService();

                UserVitalDto userVitalDto = new UserVitalDto();
                userVitalDto.setAge(tf_age.getText());
                userVitalDto.setHeight(tf_height.getText());
                userVitalDto.setCurrent_weight(tf_currentweight.getText());
                userVitalDto.setTarget_weight(tf_targetweight.getText());
                userVitalDto.setEmail(lbl_email.getText());


                userService.addUserVitals(event, userVitalDto, lbl_added);
                userService.changeScence1(event, "profile-view.fxml", "Whats your go to motivator?");
            }
        });
    }

    public void setUserInformation(String email) {
        lbl_email.setText(email);

    }

}
