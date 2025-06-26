package com.example.fitnesstrackerapp.admin.controller;

import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProgressVisualizationController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private BarChart<String, Number> workoutBarChart;

    @FXML
    private LineChart<String, Number> distanceLineChart;

    @FXML
    private ScatterChart<Number, Number> weightScatterChart;

    @FXML
    private Button btn_back;

    private String email;
    private static ProgressVisualizationController instance;

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
        instance = this;


        populateWorkoutBarChart();
        populateDistanceLineChart();
        populateWeightScatterChart();

        btn_back.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "select_view.fxml", "Select an Option," + email);
        });

    }

    public static void refreshData(int newCalories, int newWorkouts, double newDistance) {
        if (instance != null) {
            instance.updateWorkoutBarChart(newWorkouts);
            instance.updateDistanceLineChart(newDistance);
            instance.updateWeightScatterChart(newCalories, newDistance);
        }
    }

    private void populateWorkoutBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Workouts");

        String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        int[] WORKOUTS = {1, 2, 1, 3, 2, 4, 1};

        for (int i = 0; i < DAYS.length; i++) {
            series.getData().add(new XYChart.Data<>(DAYS[i], WORKOUTS[i]));
        }

        workoutBarChart.getData().add(series);
    }

    private void populateDistanceLineChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Distance (km)");

        String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        double[] DISTANCES = {0.5, 1.0, 1.5, 2.0, 1.8, 2.5, 3.0};

        for (int i = 0; i < DAYS.length; i++) {
            series.getData().add(new XYChart.Data<>(DAYS[i], DISTANCES[i]));
        }

        distanceLineChart.getData().add(series);
    }

    private void populateWeightScatterChart() {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Weight vs Calories");

        double[] WEIGHTS = {80.0, 79.8, 79.5, 79.3, 79.0, 78.7, 78.5};
        int[] CALORIES = {280, 560, 840, 1120, 900, 1260, 1400};

        for (int i = 0; i < WEIGHTS.length; i++) {
            series.getData().add(new XYChart.Data<>(CALORIES[i], WEIGHTS[i]));
        }

        weightScatterChart.getData().add(series);
    }

    private void updateWorkoutBarChart(int newWorkouts) {
        XYChart.Series<String, Number> series = workoutBarChart.getData().isEmpty() ?
                new XYChart.Series<>() : workoutBarChart.getData().get(0);

        String nextDay = "Day " + (series.getData().size() + 1);
        series.getData().add(new XYChart.Data<>(nextDay, newWorkouts));

        if (workoutBarChart.getData().isEmpty()) {
            workoutBarChart.getData().add(series);
        }
    }

    private void updateDistanceLineChart(double newDistance) {
        XYChart.Series<String, Number> series = distanceLineChart.getData().isEmpty() ?
                new XYChart.Series<>() : distanceLineChart.getData().get(0);

        String nextDay = "Day " + (series.getData().size() + 1);
        series.getData().add(new XYChart.Data<>(nextDay, newDistance));

        if (distanceLineChart.getData().isEmpty()) {
            distanceLineChart.getData().add(series);
        }
    }

    private void updateWeightScatterChart(int newCalories, double newWeight) {
        XYChart.Series<Number, Number> series = weightScatterChart.getData().isEmpty() ?
                new XYChart.Series<>() : weightScatterChart.getData().get(0);

        series.getData().add(new XYChart.Data<>(newCalories, newWeight));

        if (weightScatterChart.getData().isEmpty()) {
            weightScatterChart.getData().add(series);
        }


    }
    public static void refreshDataBasedOnWorkout(LocalDate date, String workout) {
        if (instance != null && workout != null) {
            int workoutIntensity = calculateWorkoutIntensity(workout);
            double distance = calculateDistance(workout);
            instance.updateWorkoutBarChart(workoutIntensity);
            instance.updateDistanceLineChart(distance);
        }
    }

    public static void refreshDataBasedOnNutrition(String nutrition) {
        if (instance != null && nutrition != null) {
            int calorieEffect = calculateCalorieEffect(nutrition);
            double weightChange = calculateWeightChange(nutrition);
            instance.updateWeightScatterChart(calorieEffect, weightChange);
        }
    }

    private static int calculateWorkoutIntensity(String workout) {
        return switch (workout) {
            case "BodyWeight" -> 2;
            case "Cardio" -> 3;
            case "Upper Body" -> 4;
            case "Lower Body" -> 4;
            case "HIIT" -> 5;
            case "Strength Training" -> 4;
            case "Yoga" -> 1;
            case "Core/Abs" -> 3;
            default -> 0;
        };
    }

    private static double calculateDistance(String workout) {
        return switch (workout) {
            case "BodyWeight" -> 0.5;
            case "Cardio" -> 2.0;
            case "Upper Body", "Lower Body" -> 0.8;
            case "HIIT" -> 1.5;
            case "Strength Training" -> 1.0;
            case "Yoga" -> 0.2;
            case "Core/Abs" -> 0.7;
            default -> 0.0;
        };
    }

    private static int calculateCalorieEffect(String nutrition) {
        return switch (nutrition) {
            case "Carbohydrates" -> 400;
            case "Proteins" -> 300;
            case "Healthy Fats" -> 500;
            case "Vitamins" -> 100;
            case "Minerals" -> 50;
            case "Supplements" -> 200;
            default -> 0;
        };
    }

    private static double calculateWeightChange(String nutrition) {
        return switch (nutrition) {
            case "Carbohydrates" -> 0.3;
            case "Proteins" -> -0.2;
            case "Healthy Fats" -> 0.1;
            case "Vitamins", "Minerals" -> -0.1;
            case "Supplements" -> 0.0;
            default -> 0.0;
        };
    }


}