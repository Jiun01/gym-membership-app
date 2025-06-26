package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class StartWorkoutController implements Initializable {

    @FXML
    public Label lb_start;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Label lb_finish;
    @FXML
    public Button btn_back;
    @FXML
    public Button btn_st;
    @FXML
    public Button btn_finish;

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

    lb_start.setVisible(false);
    lb_finish.setVisible(false);

    btn_st.setOnAction(event -> {
        lb_start.setVisible(true);
        lb_finish.setVisible(false);
    });

    btn_finish.setOnAction(event -> {
        lb_start.setVisible(false);
        lb_finish.setVisible(true);

        int caloriesToAdd = 280;
        int workoutsToAdd = 1;
        double distanceToAdd = 0.52;

        FitGoalsController.updateGoals(caloriesToAdd, workoutsToAdd, distanceToAdd);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "fitGoals_view.fxml", "Your Fitness Goals,"+ email);
        });
        pause.play();
    });

    btn_back.setOnAction(event -> {
        UserService userService = new UserService();
        userService.changeScence1(event, "select_view.fxml", "select an option,"+email);
    });
}
}

