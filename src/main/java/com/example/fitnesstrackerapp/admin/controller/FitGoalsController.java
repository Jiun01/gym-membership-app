package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class FitGoalsController implements Initializable {

    @FXML
    private Label caloriesLabel;

    @FXML
    private Label workoutCountLabel;

    @FXML
    private Label distanceLabel;

    @FXML
    private Button btn_back;


    public static int totalCalories = 0;
    public static int totalWorkouts = 0;
    public static double totalDistance = 0.0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateUI();
    }

    public static void updateGoals(int caloriesToAdd, int workoutsToAdd, double distanceToAdd) {
        totalCalories += caloriesToAdd;
        totalWorkouts += workoutsToAdd;
        totalDistance += distanceToAdd;

    }

    private void updateUI() {
        caloriesLabel.setText(String.valueOf(totalCalories));
        workoutCountLabel.setText(String.valueOf(totalWorkouts));
        distanceLabel.setText(String.format("%.2f", totalDistance));

        btn_back.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "select_view.fxml", "Select an Option");
        });
    }
}
