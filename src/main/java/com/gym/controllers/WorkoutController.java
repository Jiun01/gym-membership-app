package com.gym.controllers;

import com.gym.models.Workout;
import java.util.List;

public class WorkoutController {
    private Workout workout;

    public WorkoutController() {
        this.workout = new Workout();
    }

    public List<String> getExercises(String muscleGroup) {
        return workout.getExercisesFor(muscleGroup);
    }
    
    public List<String> getMuscleGroups() {
        return workout.getAllMuscleGroups();
    }
}
