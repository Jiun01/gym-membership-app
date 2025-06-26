package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.model.FitnessGoalsModel;
import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    public Button btn_workSched;
    @FXML
    public Button btn_progress;
    @FXML
    public Button btn_nutrition;
    @FXML
    public Button btn_fitGoals;
    @FXML
    public Button btn_calendar;
    @FXML
    public Button btn_profile;

    private String email;

    private boolean workoutStarted = false;

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

        btn_workSched.setOnAction(event -> {
            FitnessGoalsModel model = FitnessGoalsModel.getInstance();

            if (!model.isWorkoutStarted()) {
                model.getGoalProgress().forEach((goal, progress) -> {
                    double newProgress = Math.min(progress + 0.1, 1.0);
                    model.getGoalProgress().put(goal, newProgress);
                });


                model.setWorkoutStarted(true);
            }


            UserService userService = new UserService();
            userService.changeScence1(event, "workoutSchedule_view.fxml", "Workout Schedule,"+ email);
        });

        btn_progress.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "ProgressVisualization.fxml", "Progress Visualization,"+ email);
        });

        btn_nutrition.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "nutrition_view.fxml", "Nutrition Plan,"+ email);
        });

        btn_fitGoals.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "fGoals.fxml", "Your Fitness Goals,"+ email);
        });

        btn_calendar.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "activity-log.fxml", "Log your Activity," + email);
        });
        btn_profile.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "profile-view.fxml", "Profile,"+ email);
        });
    }

    private void incrementGoalProgress() {
        FitnessGoalsModel model = FitnessGoalsModel.getInstance();
        model.getGoalProgress().forEach((goal, progress) -> {
            double newProgress = Math.min(progress + 0.1, 1.0);
            model.getGoalProgress().put(goal, newProgress);
        });
    }
}
