package com.gymdiet.app.utils;

import com.gymdiet.app.models.User;

public class RecommendationService {

    public static class DietPlan {
        public int calories;
        public int protein;
        public int carbs;
        public int fats;

        public DietPlan(int calories, int protein, int carbs, int fats) {
            this.calories = calories;
            this.protein = protein;
            this.carbs = carbs;
            this.fats = fats;
        }
    }

    public static DietPlan generateDietPlan(User user) {
        if (user == null || user.weight == null || user.height == null || user.goal == null) {
            return new DietPlan(2200, 150, 250, 60); // Return a default plan
        }

        try {
            float weight = Float.parseFloat(user.weight);
            float height = Float.parseFloat(user.height);
            int age = 28; // Assume a default age for now

            // Calculate BMR (Mifflin-St Jeor Equation)
            double bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5; // Assuming male

            // Calculate TDEE (Total Daily Energy Expenditure) - using a moderate activity level
            double tdee = bmr * 1.55;

            int calorieGoal;
            if (user.goal.equalsIgnoreCase("Weight Loss")) {
                calorieGoal = (int) (tdee - 450); // Calorie deficit
            } else {
                calorieGoal = (int) (tdee + 450); // Calorie surplus
            }

            // Calculate Macros
            int protein = (int) (weight * 1.8); // 1.8g of protein per kg of body weight
            int fats = (int) ((calorieGoal * 0.25) / 9); // 25% of calories from fat
            int proteinCalories = protein * 4;
            int fatCalories = fats * 9;
            int carbs = (calorieGoal - proteinCalories - fatCalories) / 4;

            return new DietPlan(calorieGoal, protein, carbs, fats);

        } catch (NumberFormatException e) {
            return new DietPlan(2200, 150, 250, 60); // Return default on parsing error
        }
    }
}
