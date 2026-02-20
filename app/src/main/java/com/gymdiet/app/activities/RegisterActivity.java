package com.gymdiet.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gymdiet.app.R;
import com.gymdiet.app.databinding.ActivityRegisterBinding;
import com.gymdiet.app.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        binding.btnRegister.setOnClickListener(v -> registerUser());
        binding.tvLogin.setOnClickListener(v -> finish());
    }

    private void registerUser() {
        String name = binding.etName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String weight = binding.etWeight.getText().toString().trim();
        String height = binding.etHeight.getText().toString().trim();
        
        String goal = binding.toggleGoal.getCheckedButtonId() == R.id.btnLose ? "Weight Loss" : "Weight Gain";

        if (TextUtils.isEmpty(name)) {
            binding.tilName.setError("Name is required");
            return;
        } else {
            binding.tilName.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            binding.tilEmail.setError("Email is required");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setError("Please enter a valid email address");
            return;
        } else {
            binding.tilEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            binding.tilPassword.setError("Password is required");
            return;
        } else if (password.length() < 6) {
            binding.tilPassword.setError("Password should be at least 6 characters");
            return;
        } else {
            binding.tilPassword.setError(null);
        }

        if (TextUtils.isEmpty(weight)) {
            binding.tilWeight.setError("Weight is required");
            return;
        } else {
            binding.tilWeight.setError(null);
        }

        if (TextUtils.isEmpty(height)) {
            binding.tilHeight.setError("Height is required");
            return;
        } else {
            binding.tilHeight.setError(null);
        }

        binding.progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser().getUid();
                        User user = new User(uid, name, email);
                        user.weight = weight;
                        user.height = height;
                        user.goal = goal;
                        
                        mDatabase.child("users").child(uid).setValue(user)
                                .addOnCompleteListener(dbTask -> {
                                    binding.progressBar.setVisibility(View.GONE);
                                    if (dbTask.isSuccessful()) {
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                        finishAffinity();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Database Error: " + dbTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
