package com.example.fitnesstrackerapp.admin.formcontroller;

import com.example.fitnesstrackerapp.admin.controller.SettingsController;
import com.example.fitnesstrackerapp.admin.dto.UserDto;
import com.example.fitnesstrackerapp.admin.service.UserService;
import com.example.fitnesstrackerapp.utils.PasswordHashing;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsFormController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField tf_changename;
    @FXML
    private TextField tf_changepass;
    @FXML
    private TextField tf_changeconfirmpass;
    @FXML
    private Button btn_submitchanges;
    @FXML
    private Button btn_returnprofile;
    @FXML
    private Button btn_logout;
    @FXML
    private Label lbl_confirmchanges;
    @FXML
    public Label lbl_email;

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
        btn_submitchanges.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String password = tf_changepass.getText().trim();
                String confirmPassword = tf_changeconfirmpass.getText().trim();
                String newName = tf_changename.getText().trim();

                if (newName.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
                    lbl_confirmchanges.setText("No changes were made.");
                    return;
                }

                if (!newName.isEmpty() && !newName.matches("[a-zA-Z ]+")) {
                    lbl_confirmchanges.setText("Invalid Name! Only letters and spaces are allowed.");
                    return;
                }

                if (password.isEmpty() && confirmPassword.isEmpty()) {

                    UserDto userDto = new UserDto();
                    userDto.setEmail(email);
                    if (!newName.isEmpty()) {
                        userDto.setName(newName);
                    }

                    SettingsController settingsController = new SettingsController();
                    settingsController.updatdeUser(userDto, event, lbl_confirmchanges, null);

                    lbl_confirmchanges.setText("Changes validated and submitted successfully!");
                    return;
                }

                if (password.isEmpty() || confirmPassword.isEmpty()) {
                    lbl_confirmchanges.setText("Please fill out both password fields.");
                    return;
                }

                if (password.length() < 5 || confirmPassword.length() < 5) {
                    lbl_confirmchanges.setText("Password must be at least 5 characters long.");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    lbl_confirmchanges.setText("Passwords do not match. Please try again.");
                    return;
                }

                UserDto userDto = new UserDto();
                userDto.setEmail(email);
                if (!newName.isEmpty()) {
                    userDto.setName(newName);
                }
                userDto.setPassword(PasswordHashing.hashPassword(password));

                SettingsController settingsController = new SettingsController();
                settingsController.updatdeUser(userDto, event, lbl_confirmchanges, PasswordHashing.hashPassword(confirmPassword));

                lbl_confirmchanges.setText("Changes validated and submitted successfully!");


            }
        });
        btn_returnprofile.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "profile-view.fxml", "User Profile," + email);
        });
        btn_logout.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "existing-login.fxml", "Login To Your Account");
        });



    }

    public void setUserInformation(String email) {
        lbl_email.setText(email);

    }

}
