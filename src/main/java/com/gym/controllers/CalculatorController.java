package com.gym.controllers;

import com.gym.models.CaloriesCalculator;

public class CalculatorController {

    public double getCaloriesBurned(int age, double height, double weight, String gender, String activityLevel) {
        CaloriesCalculator calculator = new CaloriesCalculator(age, height, weight, gender, activityLevel);
        return calculator.calculateCaloriesBurned();
    }
}
