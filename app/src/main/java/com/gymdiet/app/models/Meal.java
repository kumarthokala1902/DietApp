package com.gymdiet.app.models;

public class Meal {
    private String name;
    private String items;
    private String emoji;
    private int calories;
    private int protein;
    private int carbs;
    private int fats;
    private String time;

    public Meal(String name, String items, String emoji, int calories, int protein, int carbs, int fats, String time) {
        this.name = name;
        this.items = items;
        this.emoji = emoji;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.time = time;
    }

    public String getName() { return name; }
    public String getItems() { return items; }
    public String getEmoji() { return emoji; }
    public int getCalories() { return calories; }
    public int getProtein() { return protein; }
    public int getCarbs() { return carbs; }
    public int getFats() { return fats; }
    public String getTime() { return time; }
}
