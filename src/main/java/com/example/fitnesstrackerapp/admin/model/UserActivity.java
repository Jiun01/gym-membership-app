package com.example.fitnesstrackerapp.admin.model;


import java.time.LocalDate;

public class UserActivity {
    private Long userId;
    private String date;
    private String workoutType;
    private String nutrition;

    public UserActivity() {}

    public UserActivity(Long userId, String date, String workoutType, String nutrition) {
        this.userId = userId;
        this.date = date;
        this.workoutType = workoutType;
        this.nutrition = nutrition;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "\n{\n" +
                "\"userId\": " + userId + ",\n" +
                "\"date\": \"" + date + "\",\n" +
                "\"workoutType\": \"" + workoutType + "\",\n" +
                "\"nutrition\": \"" + nutrition + "\"\n" +
                "}";
    }

}


