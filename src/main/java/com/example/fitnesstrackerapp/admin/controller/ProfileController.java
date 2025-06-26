package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.dao.DAOImpl;
import com.example.fitnesstrackerapp.admin.dto.UserDto;
import com.example.fitnesstrackerapp.admin.model.User;
import com.example.fitnesstrackerapp.admin.model.UserVital;
import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label lbl_prfname;
    @FXML
    private Label lbl_prfage;
    @FXML
    private Label lbl_prfheight;
    @FXML
    private Label lbl_prfweight;
    @FXML
    private Label lbl_prftrgtweight;
    @FXML
    private Button btn_settings;
    @FXML
    private Button btn_viewprofile;
    @FXML
    private Button btn_mainmenu;

    public String email;

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
        btn_viewprofile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DAOImpl dao = new DAOImpl();
                User user = dao.getObjbyEmail(email);
                lbl_prfname.setText(user.getName());
                Long userID = user.getUserId();

                UserVital userVital = dao.getObjVital(userID);
                lbl_prfage.setText(userVital.getAge());
                lbl_prfheight.setText(userVital.getHeight() + "cm");
                lbl_prfweight.setText(userVital.getCurrent_weight() + "kg");
                lbl_prftrgtweight.setText(userVital.getTarget_weight() + "kg");
            }
        });


        btn_settings.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "app-settings.fxml", "App Settings," + email);
        });

        btn_mainmenu.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "select_view.fxml", "Welcome to Fitness Tracker," + email);
        });

    }


}
