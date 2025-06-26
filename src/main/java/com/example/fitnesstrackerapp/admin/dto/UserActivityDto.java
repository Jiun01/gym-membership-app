package com.example.fitnesstrackerapp.admin.dto;

public class UserActivityDto {
    private String date; // String for date
    private String workoutType;
    private String nutrition;
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public String getEmail() {
        return email;
    }
}

