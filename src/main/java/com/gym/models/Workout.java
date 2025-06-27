package com.gym.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Workout {
    private Map<String, List<String>> exercisesByMuscleGroup;

    public Workout() {
        this.exercisesByMuscleGroup = new HashMap<>();
        addExercise("Chest", "Bench Press");
        addExercise("Chest", "Push-ups");
        addExercise("Back", "Pull-ups");
        addExercise("Back", "Deadlifts");
        addExercise("Legs", "Squats");
        addExercise("Legs", "Lunges");
    }

    public void addExercise(String muscleGroup, String exerciseName) {
        this.exercisesByMuscleGroup
            .computeIfAbsent(muscleGroup, k -> new ArrayList<>())
            .add(exerciseName);
    }

    public List<String> getExercisesFor(String muscleGroup) {
        return this.exercisesByMuscleGroup.getOrDefault(muscleGroup, new ArrayList<>());
    }

    public List<String> getAllMuscleGroups() {
        return new ArrayList<>(this.exercisesByMuscleGroup.keySet());
    }
}
