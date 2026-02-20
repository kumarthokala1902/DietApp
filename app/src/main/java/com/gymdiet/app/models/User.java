package com.gymdiet.app.models;

public class User {
    public String uid;
    public String name;
    public String email;
    public String goal;
    public String weight;
    public String height;
    public String profilePicUrl;
    
    // Additional Goal Fields
    public String targetWeight;
    public String weeklyWorkouts;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.goal = "Maintain Weight";
        this.weight = "70";
        this.height = "170";
        this.targetWeight = "65";
        this.weeklyWorkouts = "4";
    }
}
