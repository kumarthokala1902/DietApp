package com.gymdiet.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gymdiet.app.R;
import com.gymdiet.app.activities.MainActivity;
import com.gymdiet.app.adapters.MealAdapter;
import com.gymdiet.app.models.Meal;
import com.gymdiet.app.models.User;
import com.gymdiet.app.utils.AnimUtils;
import com.gymdiet.app.utils.DataProvider;
import com.gymdiet.app.utils.RecommendationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView tvGreeting, tvUserName, tvCaloriesConsumed, tvProtein, tvCarbs, tvFats;
    private TextView tvCalorieGoalText;
    private ProgressBar pbCalories, pbCaloriesLinear;
    private RecyclerView rvMeals;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
        }

        initViews(view);
        setupMealList();
        setupGreeting();
        setupClickListeners(view);
        loadUserData();
        
        // Start animations on the views
        startAnimations(view);
        
        return view;
    }

    private void initViews(View view) {
        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvCaloriesConsumed = view.findViewById(R.id.tvCaloriesConsumed);
        tvProtein = view.findViewById(R.id.tvProtein);
        tvCarbs = view.findViewById(R.id.tvCarbs);
        tvFats = view.findViewById(R.id.tvFats);
        tvCalorieGoalText = view.findViewById(R.id.tvCalorieGoalText);
        pbCalories = view.findViewById(R.id.pbCalories);
        pbCaloriesLinear = view.findViewById(R.id.pbCaloriesLinear);
        rvMeals = view.findViewById(R.id.rvMeals);

        AnimUtils.applyLiftAnimation(view.findViewById(R.id.cardCalories));
        AnimUtils.applyLiftAnimation(view.findViewById(R.id.cardWater));
        AnimUtils.applyLiftAnimation(view.findViewById(R.id.cardSteps));
        AnimUtils.applyLiftAnimation(view.findViewById(R.id.cardTodayWorkout));
    }

    private void loadUserData() {
        if (mUserRef == null) return;
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
                if (currentUser != null) {
                    tvUserName.setText(currentUser.name);
                    applyRecommendations(currentUser);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void applyRecommendations(User user) {
        RecommendationService.DietPlan plan = RecommendationService.generateDietPlan(user);
        
        // Update Calories UI
        if (tvCalorieGoalText != null) {
            tvCalorieGoalText.setText("of " + plan.calories + " recommended");
        }
        
        // Assuming 1840 is what they've eaten so far for demo
        int eaten = 1840;
        int progress = (int) ((eaten * 100.0) / plan.calories);
        
        AnimUtils.countUpAnimation(tvCaloriesConsumed, 0, eaten, 1200, "");
        AnimUtils.animateProgressBar(pbCalories, 0, Math.min(progress, 100), 1000);
        AnimUtils.animateProgressBar(pbCaloriesLinear, 0, Math.min(progress, 100), 1200);

        // Update Macros UI
        tvProtein.setText(plan.protein + "g");
        tvCarbs.setText(plan.carbs + "g");
        tvFats.setText(plan.fats + "g");
    }

    private void setupMealList() {
        List<Meal> meals = DataProvider.getTodayMeals();
        MealAdapter adapter = new MealAdapter(meals, (meal, position) -> {
            Toast.makeText(getContext(), "📋 " + meal.getName() + ": " + meal.getCalories() + " kcal", Toast.LENGTH_SHORT).show();
            // Pulse animation on click
            AnimUtils.pulse(rvMeals.findViewHolderForAdapterPosition(position).itemView);
        });
        rvMeals.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMeals.setAdapter(adapter);
    }

    private void setupGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String greeting = (hour < 12) ? "Good Morning! ☀️" : (hour < 17) ? "Good Afternoon! 🌤️" : "Good Evening! 🌙";
        tvGreeting.setText(greeting);
    }

    private void setupClickListeners(View view) {
        View ivAvatar = view.findViewById(R.id.ivAvatar);
        if (ivAvatar != null) ivAvatar.setOnClickListener(v -> ((MainActivity) getActivity()).switchTab(4));
    }

    private void startAnimations(View view) {
        // Slide in animations for cards
        AnimUtils.fadeIn(view.findViewById(R.id.tvGreeting), 500);
        AnimUtils.fadeIn(view.findViewById(R.id.tvUserName), 700);
        AnimUtils.slideInFromBottom(view.findViewById(R.id.cardCalories), 100);
        AnimUtils.slideInFromBottom(view.findViewById(R.id.cardWater), 250);
        AnimUtils.slideInFromBottom(view.findViewById(R.id.cardSteps), 300);
        AnimUtils.slideInFromBottom(view.findViewById(R.id.rvMeals), 450);
        AnimUtils.slideInFromBottom(view.findViewById(R.id.cardTodayWorkout), 600);
    }
}
