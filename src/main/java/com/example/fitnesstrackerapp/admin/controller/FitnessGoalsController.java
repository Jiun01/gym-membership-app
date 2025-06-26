package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.model.FitnessGoalsModel;
import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class FitnessGoalsController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    public Button btn_cont;
    @FXML
    private CheckBox cBox_bm;
    @FXML
    private CheckBox cBox_gl;
    @FXML
    private CheckBox cBox_gs;
    @FXML
    private CheckBox cBox_mcs;
    @FXML
    private CheckBox cBox_ies;
    @FXML
    private CheckBox cBox_ec;

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
        btn_cont.setDisable(true);

        cBox_bm.setOnAction(event -> handleCheckBoxClick());
        cBox_gl.setOnAction(event -> handleCheckBoxClick());
        cBox_gs.setOnAction(event -> handleCheckBoxClick());
        cBox_mcs.setOnAction(event -> handleCheckBoxClick());
        cBox_ies.setOnAction(event -> handleCheckBoxClick());
        cBox_ec.setOnAction(event -> handleCheckBoxClick());

        btn_cont.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "select_view.fxml", "Select your fitness level," + email);
        });
    }

    private void handleCheckBoxClick() {
        btn_cont.setDisable(!isAnyCheckBoxSelected());

        FitnessGoalsModel.getInstance().setSelectedGoals(getSelectedGoals());
        FitnessGoalsModel.getInstance().setGoalProgress(getGoalProgress());
    }

    private boolean isAnyCheckBoxSelected() {
        return cBox_bm.isSelected() || cBox_gl.isSelected() || cBox_gs.isSelected() ||
                cBox_mcs.isSelected() || cBox_ies.isSelected() || cBox_ec.isSelected();
    }

    private List<String> getSelectedGoals() {
        List<String> selectedGoals = new ArrayList<>();
        if (cBox_bm.isSelected()) {
            selectedGoals.add("Body Mass Goal");
        }
        if (cBox_gl.isSelected()) {
            selectedGoals.add("Get Defined Goal");
        }
        if (cBox_gs.isSelected()) {
            selectedGoals.add("Muscle Gain Goal");
        }
        if (cBox_mcs.isSelected()) {
            selectedGoals.add("Maintaining Strength Goal");
        }
        if (cBox_ies.isSelected()) {
            selectedGoals.add("Increase Endurance Goal");
        }
        if (cBox_ec.isSelected()) {
            selectedGoals.add("Consistency Goal");
        }
        return selectedGoals;
    }

    private Map<String, Double> getGoalProgress() {
        Map<String, Double> progressMap = new HashMap<>();
        if (cBox_bm.isSelected()) {
            progressMap.put("Body Mass Goal", 0.2);
        }
        if (cBox_gl.isSelected()) {
            progressMap.put("Get Defined Goal", 0.2);
        }
        if (cBox_gs.isSelected()) {
            progressMap.put("Muscle Gain Goal", 0.2);
        }
        if (cBox_mcs.isSelected()) {
            progressMap.put("Maintaining Strength Goal", 0.2);
        }
        if (cBox_ies.isSelected()) {
            progressMap.put("Increase Endurance Goal", 0.2);
        }
        if (cBox_ec.isSelected()) {
            progressMap.put("Consistency Goal", 0.2);
        }
        return progressMap;
    }
}
