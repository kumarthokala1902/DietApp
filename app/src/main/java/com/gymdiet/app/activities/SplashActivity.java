package com.gymdiet.app.activities;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gymdiet.app.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private LinearLayout logoContainer;
    private ProgressBar progressBar;
    private TextView tvVersion;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        initViews();
        startAnimations();
    }

    private void initViews() {
        logoContainer = findViewById(R.id.logoContainer);
        progressBar = findViewById(R.id.progressBar);
        tvVersion = findViewById(R.id.tvVersion);
    }

    private void startAnimations() {
        logoContainer.setScaleX(0.3f);
        logoContainer.setScaleY(0.3f);
        logoContainer.setAlpha(0f);
        logoContainer.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(800)
                .setStartDelay(200)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(this::showProgressBar)
                .start();
    }

    private void showProgressBar() {
        progressBar.setAlpha(0f);
        progressBar.setProgress(0);
        progressBar.animate()
                .alpha(1f)
                .setDuration(300)
                .start();

        ValueAnimator progressAnimator = ValueAnimator.ofInt(0, 100);
        progressAnimator.setDuration(1500);
        progressAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            progressBar.setProgress(value);
        });
        progressAnimator.start();

        tvVersion.setAlpha(0f);
        tvVersion.animate()
                .alpha(1f)
                .setDuration(400)
                .setStartDelay(400)
                .start();

        new Handler().postDelayed(this::navigateToNext, 2200);
    }

    private void navigateToNext() {
        logoContainer.animate()
                .alpha(0f)
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(300)
                .start();

        progressBar.animate()
                .alpha(0f)
                .setDuration(200)
                .withEndAction(() -> {
                    Intent intent;
                    if (mAuth.getCurrentUser() != null) {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    } else {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                })
                .start();
    }
}
