package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserEngagementController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    public Label lb_welcome, lb_one, lb_two, lb_mot, lb_prio, lb_lvl, lb_qn;
    @FXML
    public Button btn_start, btn_continue, btn_cont, btn_contFl, btn_contHo;

    @FXML
    private CheckBox cBox_loseWeight, cBox_active, cBox_bStrength, cBox_recharge, cBox_learnBasics, cBox_health;
    @FXML
    private CheckBox cBox_arms, cBox_body, cBox_Abs, cBox_Core, cBox_cardio, cBox_flexMob;
    @FXML
    private Button btn_beginner, btn_intermeddiate, btn_advanced;
    @FXML
    private Button btn_one, btn_two, btn_three, btn_four;

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

        initializeWelcomeScene();
        initializeMotivatorScene();
        initializePrioritiesScene();
        initializeFitnessLevelScene();
        initializeHowOftenScene();
    }

    private void initializeWelcomeScene() {
        if (btn_start != null) {
            btn_start.setOnAction(event -> {
                UserService userService = new UserService();
                userService.changeScence1(event, "motivator_view.fxml", "Starting and navigating your fitness journey," + email);
            });
        }
    }

    private void initializeMotivatorScene() {
        if (btn_continue != null) {
            btn_continue.setDisable(true);
            cBox_loseWeight.setOnAction(event -> handleMotivatorCheckBoxAction());
            cBox_active.setOnAction(event -> handleMotivatorCheckBoxAction());
            cBox_bStrength.setOnAction(event -> handleMotivatorCheckBoxAction());
            cBox_recharge.setOnAction(event -> handleMotivatorCheckBoxAction());
            cBox_learnBasics.setOnAction(event -> handleMotivatorCheckBoxAction());
            cBox_health.setOnAction(event -> handleMotivatorCheckBoxAction());

            btn_continue.setOnAction(event -> {
                UserService userService = new UserService();
                userService.changeScence1(event, "priorities_view.fxml", "Select your top priorities," + email);
            });
        }
    }

    private void handleMotivatorCheckBoxAction() {
        boolean anySelected = cBox_loseWeight.isSelected() || cBox_active.isSelected() ||
                cBox_bStrength.isSelected() || cBox_recharge.isSelected() ||
                cBox_learnBasics.isSelected() || cBox_health.isSelected();

        btn_continue.setDisable(!anySelected);
        lb_mot.setText(anySelected ? "You have selected an option." : "Please select at least one option.");
    }

    private void initializePrioritiesScene() {
        if (btn_cont != null) {
            btn_cont.setDisable(true);
            cBox_arms.setOnAction(event -> handlePrioritiesCheckBoxAction());
            cBox_body.setOnAction(event -> handlePrioritiesCheckBoxAction());
            cBox_Abs.setOnAction(event -> handlePrioritiesCheckBoxAction());
            cBox_Core.setOnAction(event -> handlePrioritiesCheckBoxAction());
            cBox_cardio.setOnAction(event -> handlePrioritiesCheckBoxAction());
            cBox_flexMob.setOnAction(event -> handlePrioritiesCheckBoxAction());

            btn_cont.setOnAction(event -> {
                UserService userService = new UserService();
                userService.changeScence1(event, "fitnessLevel_view.fxml", "Select your fitness level," + email);
            });
        }
    }

    private void handlePrioritiesCheckBoxAction() {
        boolean anySelected = cBox_arms.isSelected() || cBox_body.isSelected() || cBox_Abs.isSelected() ||
                cBox_Core.isSelected() || cBox_cardio.isSelected() || cBox_flexMob.isSelected();

        btn_cont.setDisable(!anySelected);
        lb_prio.setText(anySelected ? "You have selected your priorities." : "Please select at least one priority.");
    }

    private void initializeFitnessLevelScene() {
        if (btn_contFl != null) {
            btn_contFl.setDisable(true);
            btn_beginner.setOnAction(event -> handleFitnessLevelSelection(btn_beginner));
            btn_intermeddiate.setOnAction(event -> handleFitnessLevelSelection(btn_intermeddiate));
            btn_advanced.setOnAction(event -> handleFitnessLevelSelection(btn_advanced));
        }
    }

    private void handleFitnessLevelSelection(Button selectedButton) {
        resetFitnessLevelButtonStyles();
        selectedButton.setStyle("-fx-background-color: #0078d7; -fx-text-fill: white;");
        btn_contFl.setDisable(false);

        btn_contFl.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "howOften_view.fxml", "Make us know you better," + email);
        });
    }

    private void resetFitnessLevelButtonStyles() {
        btn_beginner.setStyle("");
        btn_intermeddiate.setStyle("");
        btn_advanced.setStyle("");
    }

    private void initializeHowOftenScene() {
        if (btn_contHo != null) {
            btn_contHo.setDisable(true);
            btn_one.setOnAction(event -> handleHowOftenSelection(btn_one));
            btn_two.setOnAction(event -> handleHowOftenSelection(btn_two));
            btn_three.setOnAction(event -> handleHowOftenSelection(btn_three));
            btn_four.setOnAction(event -> handleHowOftenSelection(btn_four));
        }
    }

    private void handleHowOftenSelection(Button selectedButton) {
        resetHowOftenButtonStyles();
        selectedButton.setStyle("-fx-background-color: #0078d7; -fx-text-fill: white;");
        btn_contHo.setDisable(false);

        btn_contHo.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "fitnessGoals_view.fxml", "Set your fitness goals," + email);
        });
    }

    private void resetHowOftenButtonStyles() {
        btn_one.setStyle("");
        btn_two.setStyle("");
        btn_three.setStyle("");
        btn_four.setStyle("");
    }
}
