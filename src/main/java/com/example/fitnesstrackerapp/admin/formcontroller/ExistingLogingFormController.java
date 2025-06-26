package com.example.fitnesstrackerapp.admin.formcontroller;

import com.example.fitnesstrackerapp.admin.controller.ExistingLogInController;
import com.example.fitnesstrackerapp.admin.dao.DAOImpl;
import com.example.fitnesstrackerapp.admin.dto.UserDto;
import com.example.fitnesstrackerapp.admin.model.User;
import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ExistingLogingFormController implements Initializable {
    @FXML
    public Label lblgetstarted;
    @FXML
    private Button btn_login;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_password;
    @FXML
    private Button btn_signup;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StringBuilder missingFields = new StringBuilder("Details Incomplete! Please fill in");
                boolean isIncomplete = false;

                if (tf_email.getText().isEmpty()) {
                    missingFields.append(" email");
                    isIncomplete = true;
                }

                if (tf_password.getText().isEmpty()) {
                    if (isIncomplete) missingFields.append(" and");
                    missingFields.append(" password");
                    isIncomplete = true;
                }

                if (tf_email.getText().isEmpty() && tf_password.getText().isEmpty()) {
                    lblgetstarted.setText("Details Incomplete! Please fill in all details");
                    return;
                }

                if (isIncomplete) {
                    lblgetstarted.setText(missingFields.toString());
                    return;
                }

                if (!validateEmail(tf_email.getText())) {
                    lblgetstarted.setText("Invalid Email! Please enter a valid email address.");
                    return;
                }
                UserDto userDto = new UserDto();

                DAOImpl dao = new DAOImpl();
                User user = dao.getObjbyEmail(tf_email.getText());
                if (user!=null) {
                    userDto.setUserId(user.getUserId());
                }

                userDto.setEmail(tf_email.getText());
                userDto.setPassword(tf_password.getText());
                userDto.setName(tf_email.getText());
                userDto.setUserId(userDto.getUserId());


                ExistingLogInController existingLogInController = new ExistingLogInController();
                existingLogInController.validateExistingUser(userDto, event, lblgetstarted);


            }
        });
        btn_signup.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "user-signin.fxml", "Create an Account");
        });


    }

    private boolean validateEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.com$");
    }
}
