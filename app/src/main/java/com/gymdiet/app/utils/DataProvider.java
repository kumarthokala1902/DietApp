package com.gymdiet.app.utils;

import com.gymdiet.app.models.DietPlan;
import com.gymdiet.app.models.Meal;
import com.gymdiet.app.models.Workout;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    public static List<Meal> getTodayMeals() {
        List<Meal> meals = new ArrayList<>();
        meals.add(new Meal("Breakfast", "Oats, Eggs, Banana, Milk", "🍳", 480, 32, 62, 12, "7:00 AM"));
        meals.add(new Meal("Mid Morning", "Protein Shake, Almonds", "🥤", 280, 35, 18, 10, "10:30 AM"));
        meals.add(new Meal("Lunch", "Chicken Rice, Broccoli, Salad", "🍗", 620, 45, 68, 14, "1:00 PM"));
        meals.add(new Meal("Pre Workout", "Banana, Peanut Butter Toast", "🍌", 310, 12, 48, 14, "4:00 PM"));
        meals.add(new Meal("Dinner", "Salmon, Sweet Potato, Veggies", "🐟", 540, 42, 38, 20, "8:00 PM"));
        return meals;
    }

    public static List<DietPlan> getDietPlans() {
        List<DietPlan> plans = new ArrayList<>();
        plans.add(new DietPlan("Muscle Gain Plan", "BULKING", "🥩", 3200, 5, 12, 0));
        plans.add(new DietPlan("Fat Loss Diet", "CUTTING", "🥗", 1800, 4, 8, 1));
        plans.add(new DietPlan("Maintenance Plan", "MAINTAIN", "⚖️", 2200, 4, 16, 2));
        plans.add(new DietPlan("Plant Power Diet", "VEGAN", "🌱", 2000, 5, 12, 1));
        plans.add(new DietPlan("Keto Performance", "KETO", "🥑", 2400, 4, 10, 0));
        plans.add(new DietPlan("Lean Bulk Protocol", "BULKING", "💪", 2800, 6, 14, 2));
        return plans;
    }

    public static List<Workout> getWorkouts() {
        List<Workout> workouts = new ArrayList<>();
        // Chest
        workouts.add(new Workout("Bench Press", "Chest • Triceps • Shoulders", "🏋️", 4, "8-12 reps", "Advanced", "Chest"));
        workouts.add(new Workout("Push-Ups", "Chest • Triceps", "🤸", 4, "15-20 reps", "Beginner", "Chest"));
        workouts.add(new Workout("Incline Dumbbell Press", "Upper Chest", "💪", 3, "10-12 reps", "Intermediate", "Chest"));
        // Back
        workouts.add(new Workout("Pull-Ups", "Back • Biceps", "🦾", 4, "6-10 reps", "Intermediate", "Back"));
        workouts.add(new Workout("Bent Over Row", "Back • Biceps", "🏋️", 4, "8-12 reps", "Intermediate", "Back"));
        workouts.add(new Workout("Lat Pulldown", "Latissimus Dorsi", "💪", 3, "10-15 reps", "Beginner", "Back"));
        // Legs
        workouts.add(new Workout("Barbell Squat", "Quads • Glutes • Core", "🦵", 5, "5-8 reps", "Advanced", "Legs"));
        workouts.add(new Workout("Romanian Deadlift", "Hamstrings • Glutes", "🏋️", 4, "8-12 reps", "Intermediate", "Legs"));
        workouts.add(new Workout("Leg Press", "Quads • Glutes", "💺", 4, "10-15 reps", "Beginner", "Legs"));
        // Arms
        workouts.add(new Workout("Barbell Curl", "Biceps", "💪", 4, "10-12 reps", "Beginner", "Arms"));
        workouts.add(new Workout("Tricep Pushdown", "Triceps", "🏋️", 4, "12-15 reps", "Beginner", "Arms"));
        workouts.add(new Workout("Hammer Curls", "Biceps • Brachialis", "🔨", 3, "12-15 reps", "Beginner", "Arms"));
        // Core
        workouts.add(new Workout("Plank", "Core • Stability", "🤸", 3, "60 sec", "Beginner", "Core"));
        workouts.add(new Workout("Hanging Leg Raises", "Abs • Hip Flexors", "🦵", 4, "12-15 reps", "Advanced", "Core"));
        workouts.add(new Workout("Cable Crunch", "Abs", "💪", 3, "15-20 reps", "Intermediate", "Core"));
        return workouts;
    }
}
