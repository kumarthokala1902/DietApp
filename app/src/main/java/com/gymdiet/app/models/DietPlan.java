package com.gymdiet.app.models;

public class DietPlan {
    private String name;
    private String category;
    private String emoji;
    private int calories;
    private int mealsPerDay;
    private int durationWeeks;
    private int gradientType; // 0=orange, 1=green, 2=purple

    public DietPlan(String name, String category, String emoji, int calories, int mealsPerDay, int durationWeeks, int gradientType) {
        this.name = name;
        this.category = category;
        this.emoji = emoji;
        this.calories = calories;
        this.mealsPerDay = mealsPerDay;
        this.durationWeeks = durationWeeks;
        this.gradientType = gradientType;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getEmoji() { return emoji; }
    public int getCalories() { return calories; }
    public int getMealsPerDay() { return mealsPerDay; }
    public int getDurationWeeks() { return durationWeeks; }
    public int getGradientType() { return gradientType; }
}
