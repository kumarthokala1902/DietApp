package com.gymdiet.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.gymdiet.app.R;
import com.gymdiet.app.activities.LoginActivity;
import com.gymdiet.app.models.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView tvProfileName, tvProfileGoal, tvWeight, tvHeight, tvBMI;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
        }

        initViews(view);
        setupClickListeners(view);
        loadUserData();
        
        return view;
    }

    private void initViews(View view) {
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileGoal = view.findViewById(R.id.tvProfileGoal);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvHeight = view.findViewById(R.id.tvHeight);
        tvBMI = view.findViewById(R.id.tvBMI);
    }

    private void setupClickListeners(View view) {
        View btnEditTop = view.findViewById(R.id.btnEditProfileTop);
        View itemEditInfo = view.findViewById(R.id.itemEditProfile);
        View itemGoals = view.findViewById(R.id.itemGoals);
        
        if (btnEditTop != null) btnEditTop.setOnClickListener(v -> showEditProfileDialog());
        if (itemEditInfo != null) itemEditInfo.setOnClickListener(v -> showEditProfileDialog());
        if (itemGoals != null) itemGoals.setOnClickListener(v -> showMyGoalsDialog());

        view.findViewById(R.id.btnLogout).setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });
    }

    private void loadUserData() {
        if (mDatabase == null) return;

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
                if (currentUser != null) {
                    tvProfileName.setText(currentUser.name);
                    tvProfileGoal.setText("🎯 " + currentUser.goal);
                    tvWeight.setText(currentUser.weight);
                    tvHeight.setText(currentUser.height);
                    calculateBMI(currentUser.weight, currentUser.height);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error loading profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculateBMI(String weightStr, String heightStr) {
        try {
            float weight = Float.parseFloat(weightStr);
            float height = Float.parseFloat(heightStr) / 100;
            if (height > 0) {
                float bmi = weight / (height * height);
                tvBMI.setText(String.format("%.1f", bmi));
            }
        } catch (Exception e) {
            tvBMI.setText("--");
        }
    }

    private void showEditProfileDialog() {
        if (currentUser == null) return;

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_profile, null);
        TextInputEditText etName = dialogView.findViewById(R.id.etEditName);
        TextInputEditText etGoal = dialogView.findViewById(R.id.etEditGoal);
        TextInputEditText etWeight = dialogView.findViewById(R.id.etEditWeight);
        TextInputEditText etHeight = dialogView.findViewById(R.id.etEditHeight);

        etName.setText(currentUser.name);
        etGoal.setText(currentUser.goal);
        etWeight.setText(currentUser.weight);
        etHeight.setText(currentUser.height);

        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.Theme_GymDiet)
                .setView(dialogView)
                .create();

        dialogView.findViewById(R.id.btnSaveProfile).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String goal = etGoal.getText().toString().trim();
            String weight = etWeight.getText().toString().trim();
            String height = etHeight.getText().toString().trim();

            if (name.isEmpty()) return;

            currentUser.name = name;
            currentUser.goal = goal;
            currentUser.weight = weight;
            currentUser.height = height;

            mDatabase.setValue(currentUser).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        });

        dialog.show();
    }

    private void showMyGoalsDialog() {
        if (currentUser == null) return;

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_my_goals, null);
        TextInputEditText etTargetWeight = dialogView.findViewById(R.id.etTargetWeight);
        TextInputEditText etWeeklyWorkouts = dialogView.findViewById(R.id.etWeeklyWorkouts);

        etTargetWeight.setText(currentUser.targetWeight);
        etWeeklyWorkouts.setText(currentUser.weeklyWorkouts);

        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.Theme_GymDiet)
                .setView(dialogView)
                .create();

        dialogView.findViewById(R.id.btnSaveGoals).setOnClickListener(v -> {
            String targetWeight = etTargetWeight.getText().toString().trim();
            String weeklyWorkouts = etWeeklyWorkouts.getText().toString().trim();

            if (targetWeight.isEmpty() || weeklyWorkouts.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            currentUser.targetWeight = targetWeight;
            currentUser.weeklyWorkouts = weeklyWorkouts;

            mDatabase.setValue(currentUser).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Goals Updated Successfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        });

        dialog.show();
    }
}
