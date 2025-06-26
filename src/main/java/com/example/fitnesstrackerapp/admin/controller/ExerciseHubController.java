package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ExerciseHubController implements Initializable {

    @FXML
    private Button btn_backWorkoutSchedule;
    @FXML
    private AnchorPane anchorPane;

    private String email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            try {
                String title = ((Stage) anchorPane.getScene().getWindow()).getTitle();


                if (title.contains(",")) {
                    email = title.split(",")[1].trim();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btn_backWorkoutSchedule.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "workoutSchedule_view.fxml", "Select your fitness level,"+ email);
        });
    }
}
