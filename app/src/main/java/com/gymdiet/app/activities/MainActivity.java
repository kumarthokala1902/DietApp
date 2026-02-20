package com.gymdiet.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.gymdiet.app.R;
import com.gymdiet.app.fragments.DietFragment;
import com.gymdiet.app.fragments.HomeFragment;
import com.gymdiet.app.fragments.ProfileFragment;
import com.gymdiet.app.fragments.TrackerFragment;
import com.gymdiet.app.fragments.WorkoutFragment;
import com.gymdiet.app.utils.AnimUtils;

public class MainActivity extends AppCompatActivity {

    private LinearLayout navHome, navDiet, navTracker, navProfile;
    private FrameLayout navWorkout;
    private FrameLayout fragmentContainer;

    private int currentTab = 0; // 0=home, 1=diet, 2=workout, 3=tracker, 4=profile

    private TextView tvHome, tvDiet, tvTracker, tvProfile;
    private ImageView ivHome, ivDiet, ivTracker, ivProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupNavigation();

        loadFragment(new HomeFragment(), false);
        setActiveTab(0);
    }

    private void initViews() {
        navHome = findViewById(R.id.navHome);
        navDiet = findViewById(R.id.navDiet);
        navWorkout = findViewById(R.id.navWorkout);
        navTracker = findViewById(R.id.navTracker);
        navProfile = findViewById(R.id.navProfile);
        fragmentContainer = findViewById(R.id.fragmentContainer);

        tvHome = findViewById(R.id.tvHome);
        tvDiet = findViewById(R.id.tvDiet);
        tvTracker = findViewById(R.id.tvTracker);
        tvProfile = findViewById(R.id.tvProfile);

        ivHome = findViewById(R.id.ivHome);
        ivDiet = findViewById(R.id.ivDiet);
        ivTracker = findViewById(R.id.ivTracker);
        ivProfile = findViewById(R.id.ivProfile);
    }

    private void setupNavigation() {
        navHome.setOnClickListener(v -> switchTab(0));
        navDiet.setOnClickListener(v -> switchTab(1));
        navWorkout.setOnClickListener(v -> switchTab(2));
        navTracker.setOnClickListener(v -> switchTab(3));
        navProfile.setOnClickListener(v -> switchTab(4));
    }

    public void switchTab(int tab) {
        if (currentTab == tab && tab != 2) return; // Allow workout reload but not others
        
        currentTab = tab;
        Fragment fragment;
        boolean animate = true;

        switch (tab) {
            case 0:
                fragment = new HomeFragment();
                animate = false;
                break;
            case 1: fragment = new DietFragment(); break;
            case 2: fragment = new WorkoutFragment(); break;
            case 3: fragment = new TrackerFragment(); break;
            case 4: fragment = new ProfileFragment(); break;
            default: return;
        }

        loadFragment(fragment, animate);
        setActiveTab(tab);
    }

    private void loadFragment(Fragment fragment, boolean animate) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (animate) {
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        }
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    private void setActiveTab(int tab) {
        int activeColor = 0xFFFF6B35;
        int inactiveColor = 0xFF8888AA;

        tvHome.setTextColor(inactiveColor);
        tvDiet.setTextColor(inactiveColor);
        tvTracker.setTextColor(inactiveColor);
        tvProfile.setTextColor(inactiveColor);

        ivHome.setColorFilter(inactiveColor);
        ivDiet.setColorFilter(inactiveColor);
        ivTracker.setColorFilter(inactiveColor);
        ivProfile.setColorFilter(inactiveColor);

        switch (tab) {
            case 0:
                tvHome.setTextColor(activeColor);
                ivHome.setColorFilter(activeColor);
                animateNavIcon(ivHome);
                break;
            case 1:
                tvDiet.setTextColor(activeColor);
                ivDiet.setColorFilter(activeColor);
                animateNavIcon(ivDiet);
                break;
            case 3:
                tvTracker.setTextColor(activeColor);
                ivTracker.setColorFilter(activeColor);
                animateNavIcon(ivTracker);
                break;
            case 4:
                tvProfile.setTextColor(activeColor);
                ivProfile.setColorFilter(activeColor);
                animateNavIcon(ivProfile);
                break;
        }
    }

    private void animateNavIcon(View icon) {
        icon.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(100)
                .withEndAction(() -> icon.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start())
                .start();
    }
}
