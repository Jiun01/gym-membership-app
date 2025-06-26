package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkoutScheduleController implements Initializable {
    @FXML
    AnchorPane anchorPane;
    @FXML
    public Label lb_workout;
    @FXML
    public Label lb_schedule;
    @FXML
    public Button btn_bodyweight;
    @FXML
    public Button btn_core;
    @FXML
    public Button btn_lowerbody;
    @FXML
    public Button btn_upperbody;
    @FXML
    public Button btn_hit;
    @FXML
    public Button btn_yoga;
    @FXML
    public Button btn_cardio;
    @FXML
    public Button btn_strenthtraining;
    @FXML
    public Button btn_back;

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
        btn_strenthtraining.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "StrengthTraining.fxml", "Select your fitness level," + email);
        });
        btn_cardio.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "Cardio.fxml", "Select your fitness level," + email);
        });
        btn_yoga.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "Yoga.fxml", "Select your fitness level," + email);
        });
        btn_hit.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "Hiit.fxml", "Select your fitness level," + email);
        });
        btn_upperbody.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "UpperBody.fxml", "Select your fitness level," + email);
        });
        btn_core.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "Core.fxml", "Select your fitness level," + email);
        });
        btn_bodyweight.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "BodyWeight.fxml", "Select your fitness level," + email);
        });
        btn_lowerbody.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "LowerBody.fxml", "Select your fitness level," + email);
        });


        btn_back.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "select_view.fxml", "select an option," + email);


        });

    }
}
