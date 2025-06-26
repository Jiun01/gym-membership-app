package com.example.fitnesstrackerapp.admin.formcontroller;

import com.example.fitnesstrackerapp.admin.controller.VitalsController;
import com.example.fitnesstrackerapp.admin.dto.UserVitalDto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class VitalsFormController implements Initializable {
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
                StringBuilder missingFields = new StringBuilder("Details Incomplete! Please fill in");
                boolean isIncomplete = false;

                if (tf_age.getText().isEmpty()) {
                    missingFields.append(" age");
                    isIncomplete = true;
                }

                if (tf_height.getText().isEmpty()) {
                    if (isIncomplete) missingFields.append(" and");
                    missingFields.append(" height");
                    isIncomplete = true;
                }

                if (tf_currentweight.getText().isEmpty()) {
                    if (isIncomplete) missingFields.append(" and");
                    missingFields.append(" current weight");
                    isIncomplete = true;
                }

                if (tf_targetweight.getText().isEmpty()) {
                    if (isIncomplete) missingFields.append(" and");
                    missingFields.append(" target weight");
                    isIncomplete = true;
                }

                if (tf_age.getText().isEmpty() && tf_height.getText().isEmpty() &&
                        tf_currentweight.getText().isEmpty() && tf_targetweight.getText().isEmpty()) {
                    lbl_checker.setText("Details Incomplete! Please fill in all details");
                    return;
                }

                if (isIncomplete) {
                    lbl_checker.setText(missingFields.toString());
                    return;
                }


                UserVitalDto userVitalDto = new UserVitalDto();
                userVitalDto.setAge(tf_age.getText());
                userVitalDto.setHeight(tf_height.getText());
                userVitalDto.setCurrent_weight(tf_currentweight.getText());
                userVitalDto.setTarget_weight(tf_targetweight.getText());
                userVitalDto.setEmail(lbl_email.getText());


                VitalsController vitalsController = new VitalsController();
                vitalsController.userVital(userVitalDto, event, lbl_added);
            }
        });
    }

    public void setUserInformation(String email) {
        lbl_email.setText(email);

    }

}
