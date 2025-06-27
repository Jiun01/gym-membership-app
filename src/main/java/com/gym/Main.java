package com.gym;

import com.gym.controllers.AuthController;
import com.gym.controllers.MemberController;
import com.gym.models.Member;
import com.gym.models.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private AuthController authController = new AuthController();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gym Membership App");

        VBox loginLayout = new VBox(10);
        Label userIdLabel = new Label("User ID: member1");
        Label passwordLabel = new Label("Password: pass123");
        Button loginButton = new Button("Login as Member");
        
        loginLayout.getChildren().addAll(userIdLabel, passwordLabel, loginButton);
        Scene loginScene = new Scene(loginLayout, 300, 200);

        loginButton.setOnAction(e -> {
            User user = authController.login("member1", "pass123");
            if (user instanceof Member) {
                showMemberDashboard(primaryStage, (Member) user);
            }
        });

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void showMemberDashboard(Stage stage, Member member) {
        MemberController memberController = new MemberController(member);

        VBox dashboardLayout = new VBox(10);
        Label welcomeLabel = new Label("Welcome, " + member.getName());
        Label profileLabel = new Label("Profile Info:");
        Label profileDetails = new Label(memberController.viewProfile());
        Label membershipLabel = new Label("Membership Info:");
        Label membershipDetails = new Label(memberController.viewMembership());

        dashboardLayout.getChildren().addAll(
            welcomeLabel, profileLabel, profileDetails, membershipLabel, membershipDetails
        );
        Scene dashboardScene = new Scene(dashboardLayout, 400, 300);
        stage.setScene(dashboardScene);
    }
}
