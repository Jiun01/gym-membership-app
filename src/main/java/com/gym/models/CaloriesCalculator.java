package com.gym.models;

public class CaloriesCalculator {

    private int age;
    private double height; // in cm
    private double weight; // in kg
    private String gender; // "male" or "female"
    private String activityLevel; // "sedentary", "light", "moderate", "active", "very_active"

    public CaloriesCalculator(int age, double height, double weight, String gender, String activityLevel) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.activityLevel = activityLevel;
    }
    
    public double calculateBMR() {
        if ("male".equalsIgnoreCase(gender)) {
            return 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else { // female
            return 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
    }

    public double calculateCaloriesBurned() {
        double bmr = calculateBMR();
        double activityMultiplier;

        switch (activityLevel.toLowerCase()) {
            case "sedentary":
                activityMultiplier = 1.2;
                break;
            case "light":
                activityMultiplier = 1.375;
                break;
            case "moderate":
                activityMultiplier = 1.55;
                break;
            case "active":
                activityMultiplier = 1.725;
                break;
            case "very_active":
                activityMultiplier = 1.9;
                break;
            default:
                activityMultiplier = 1.2; // Default to sedentary
                break;
        }

        return bmr * activityMultiplier;
    }
}
