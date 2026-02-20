package com.gymdiet.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.gymdiet.app.R;
import com.gymdiet.app.adapters.OnboardingAdapter;
import com.gymdiet.app.utils.AnimUtils;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private MaterialButton btnNext;
    private TextView tvSkip;
    private View dot1, dot2, dot3;

    private int currentPage = 0;

    private String[] titles = {
            "Track Your Nutrition",
            "Personalized Workouts",
            "Achieve Your Goals"
    };

    private String[] descriptions = {
            "Monitor every calorie, protein, carb and fat with precision and ease. Know exactly what fuels your body.",
            "Follow expert-curated workout routines tailored specifically to your fitness level and goals.",
            "Track your progress daily, stay motivated with streaks and reach your dream physique faster."
    };

    private String[] emojis = { "🥗", "🏋️", "🏆" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        initViews();
        setupViewPager();
        setupClickListeners();
        animateEntrance();
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        tvSkip = findViewById(R.id.tvSkip);
        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);
    }

    private void setupViewPager() {
        OnboardingAdapter adapter = new OnboardingAdapter(titles, descriptions, emojis);
        viewPager.setAdapter(adapter);

        // Add page change callback
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                updateDots(position);
                updateButton(position);
            }
        });
    }

    private void setupClickListeners() {
        btnNext.setOnClickListener(v -> {
            AnimUtils.pulse(v);
            if (currentPage < titles.length - 1) {
                viewPager.setCurrentItem(currentPage + 1, true);
            } else {
                goToMain();
            }
        });

        tvSkip.setOnClickListener(v -> goToMain());
    }

    private void updateDots(int page) {
        // Reset all
        dot1.setLayoutParams(getInactiveDotParams());
        dot2.setLayoutParams(getInactiveDotParams());
        dot3.setLayoutParams(getInactiveDotParams());

        dot1.setBackgroundResource(R.drawable.dot_inactive);
        dot2.setBackgroundResource(R.drawable.dot_inactive);
        dot3.setBackgroundResource(R.drawable.dot_inactive);

        // Active
        View activeDot;
        switch (page) {
            case 0: activeDot = dot1; break;
            case 1: activeDot = dot2; break;
            default: activeDot = dot3; break;
        }
        activeDot.setBackgroundResource(R.drawable.dot_active);
        activeDot.setLayoutParams(getActiveDotParams());
    }

    private android.view.ViewGroup.LayoutParams getActiveDotParams() {
        android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
                dpToPx(24), dpToPx(8));
        params.setMarginEnd(dpToPx(6));
        return params;
    }

    private android.view.ViewGroup.LayoutParams getInactiveDotParams() {
        android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
                dpToPx(8), dpToPx(8));
        params.setMarginEnd(dpToPx(6));
        return params;
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private void updateButton(int page) {
        if (page == titles.length - 1) {
            btnNext.setText("Get Started! 🚀");
        } else {
            btnNext.setText("Next →");
        }
        AnimUtils.pulse(btnNext);
    }

    private void animateEntrance() {
        btnNext.setAlpha(0f);
        btnNext.setTranslationY(50f);
        btnNext.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .setStartDelay(600)
                .start();
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}
