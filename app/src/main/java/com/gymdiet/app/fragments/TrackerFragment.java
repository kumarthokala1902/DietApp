package com.gymdiet.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gymdiet.app.R;
import com.gymdiet.app.models.User;
import com.gymdiet.app.utils.AnimUtils;
import com.gymdiet.app.utils.RecommendationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackerFragment extends Fragment {

    private ProgressBar pbDailyCalories, pbProtein, pbCarbs, pbFats;
    private TextView tvCaloriesEaten, tvCaloriesLeft;
    private TextView tvProteinAmount, tvCarbsAmount, tvFatsAmount;
    private LinearLayout llWaterGlasses;
    private TextView tvWaterAmount;

    private int waterGlasses = 0;
    private int totalGlasses = 12;
    
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
        }

        initViews(view);
        setupWaterTracker(view);
        loadUserData();
        
        return view;
    }

    private void initViews(View view) {
        pbDailyCalories = view.findViewById(R.id.pbDailyCalories);
        pbProtein = view.findViewById(R.id.pbProtein);
        pbCarbs = view.findViewById(R.id.pbCarbs);
        pbFats = view.findViewById(R.id.pbFats);

        tvCaloriesEaten = view.findViewById(R.id.tvCaloriesEaten);
        tvCaloriesLeft = view.findViewById(R.id.tvCaloriesLeft);
        tvProteinAmount = view.findViewById(R.id.tvProteinAmount);
        tvCarbsAmount = view.findViewById(R.id.tvCarbsAmount);
        tvFatsAmount = view.findViewById(R.id.tvFatsAmount);

        llWaterGlasses = view.findViewById(R.id.llWaterGlasses);
        tvWaterAmount = view.findViewById(R.id.tvWaterAmount);
    }

    private void loadUserData() {
        if (mUserRef == null) return;
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    applyRecommendations(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void applyRecommendations(User user) {
        RecommendationService.DietPlan plan = RecommendationService.generateDietPlan(user);
        
        int eaten = 1840; // demo data
        int left = plan.calories - eaten;
        
        AnimUtils.countUpAnimation(tvCaloriesEaten, 0, eaten, 1000, "");
        AnimUtils.countUpAnimation(tvCaloriesLeft, 0, Math.max(left, 0), 1000, "");
        
        int progress = (int) ((eaten * 100.0) / plan.calories);
        AnimUtils.animateProgressBar(pbDailyCalories, 0, Math.min(progress, 100), 1000);

        tvProteinAmount.setText("of " + plan.protein + "g");
        tvCarbsAmount.setText("of " + plan.carbs + "g");
        tvFatsAmount.setText("of " + plan.fats + "g");
        
        // Demo macro progress
        AnimUtils.animateProgressBar(pbProtein, 0, 75, 1200);
        AnimUtils.animateProgressBar(pbCarbs, 0, 60, 1200);
        AnimUtils.animateProgressBar(pbFats, 0, 85, 1200);
    }

    private void setupWaterTracker(View view) {
        buildWaterGlasses();
        updateWaterText();

        view.findViewById(R.id.btnAddWater).setOnClickListener(v -> {
            if (waterGlasses < totalGlasses) {
                waterGlasses++;
                AnimUtils.pulse(v);
                buildWaterGlasses();
                updateWaterText();
            } else {
                AnimUtils.shake(v);
                Toast.makeText(getContext(), "Goal Reached! 💧", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buildWaterGlasses() {
        if (llWaterGlasses == null) return;
        llWaterGlasses.removeAllViews();
        int dpSize = (int) (35 * getResources().getDisplayMetrics().density);
        for (int i = 0; i < totalGlasses; i++) {
            TextView glass = new TextView(getContext());
            glass.setLayoutParams(new LinearLayout.LayoutParams(dpSize, dpSize));
            glass.setGravity(android.view.Gravity.CENTER);
            glass.setText(i < waterGlasses ? "💧" : "🫙");
            llWaterGlasses.addView(glass);
        }
    }

    private void updateWaterText() {
        tvWaterAmount.setText(String.format("%.1f / 3.0 L", waterGlasses * 0.25f));
    }
}
