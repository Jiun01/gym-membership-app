package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.model.FitnessGoalsModel;
import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class FGoalsController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private VBox vbox_goals; // VBox to hold the goals
    @FXML
    public Button btn_back;
    @FXML
    public Button btn_change;

    private String email;

    public void initialize() {
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

        btn_back.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "select_view.fxml", "Select an Option," + email);
        });


        btn_change.setOnAction(event -> {
            FitnessGoalsModel.getInstance().setWorkoutStarted(false);
            UserService userService = new UserService();
            userService.changeScence1(event, "fitnessGoals_view.fxml", "Select Fitness Goals," + email);
        });


        setGoals();
    }

    public void setGoals() {
        FitnessGoalsModel model = FitnessGoalsModel.getInstance();


        vbox_goals.getChildren().clear();


        if (!model.isWorkoutStarted()) {
            Label messageLabel = new Label("Start a workout to track your progress.");
            vbox_goals.getChildren().add(messageLabel);
            return;
        }

        List<String> goals = model.getSelectedGoals();

        for (String goal : goals) {
            HBox hbox = new HBox(10);
            Label goalLabel = new Label(goal);
            ProgressBar progressBar = new ProgressBar(0);


            progressBar.setProgress(getCurrentProgress(goal));

            hbox.getChildren().addAll(goalLabel, progressBar);
            vbox_goals.getChildren().add(hbox);
        }
    }

    private double getCurrentProgress(String goal) {
        return FitnessGoalsModel.getInstance().getGoalProgress().getOrDefault(goal, 0.0);
    }
}