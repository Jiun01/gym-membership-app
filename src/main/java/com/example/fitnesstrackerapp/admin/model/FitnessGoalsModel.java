package com.example.fitnesstrackerapp.admin.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FitnessGoalsModel {
    private static FitnessGoalsModel instance;
    private List<String> selectedGoals;
    private Map<String, Double> goalProgress;
    private boolean workoutStarted;

    private FitnessGoalsModel() {
        goalProgress = new HashMap<>();
        workoutStarted = false;
    }

    public static FitnessGoalsModel getInstance() {
        if (instance == null) {
            instance = new FitnessGoalsModel();
        }
        return instance;
    }

    public List<String> getSelectedGoals() {
        return selectedGoals;
    }

    public void setSelectedGoals(List<String> selectedGoals) {
        this.selectedGoals = selectedGoals;
    }

    public Map<String, Double> getGoalProgress() {
        return goalProgress;
    }

    public void setGoalProgress(Map<String, Double> goalProgress) {
        this.goalProgress = goalProgress;
    }

    public boolean isWorkoutStarted() {
        return workoutStarted;
    }

    public void setWorkoutStarted(boolean workoutStarted) {
        this.workoutStarted = workoutStarted;
    }

    public void resetProgress() {
        goalProgress.replaceAll((goal, progress) -> 0.0);
        workoutStarted = false;
    }
}
