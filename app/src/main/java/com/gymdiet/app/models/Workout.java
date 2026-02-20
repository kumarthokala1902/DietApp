package com.gymdiet.app.models;

public class Workout {
    private String name;
    private String muscleGroup;
    private String emoji;
    private int sets;
    private String reps;
    private String difficulty;
    private String category;

    public Workout(String name, String muscleGroup, String emoji, int sets, String reps, String difficulty, String category) {
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.emoji = emoji;
        this.sets = sets;
        this.reps = reps;
        this.difficulty = difficulty;
        this.category = category;
    }

    public String getName() { return name; }
    public String getMuscleGroup() { return muscleGroup; }
    public String getEmoji() { return emoji; }
    public int getSets() { return sets; }
    public String getReps() { return reps; }
    public String getDifficulty() { return difficulty; }
    public String getCategory() { return category; }
}
