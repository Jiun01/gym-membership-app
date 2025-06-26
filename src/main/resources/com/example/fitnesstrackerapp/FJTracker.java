package com.example.fitnesstrackerapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FJTracker extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file from the correct path
        FXMLLoader fxmlLoader = new FXMLLoader(FJTracker.class.getResource("logo-screen.fxml"));

        // Load the scene
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        // Set the stage title and scene
        stage.setTitle("FitJourney - Your Very Own Fitness Tracker Application");
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

