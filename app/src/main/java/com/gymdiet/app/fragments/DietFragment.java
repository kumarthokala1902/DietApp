package com.gymdiet.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gymdiet.app.R;
import com.gymdiet.app.adapters.DietPlanAdapter;
import com.gymdiet.app.models.DietPlan;
import com.gymdiet.app.utils.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class DietFragment extends Fragment {

    private RecyclerView rvDietPlans;
    private DietPlanAdapter adapter;
    private List<DietPlan> allPlans;
    private List<DietPlan> filteredPlans;

    private TextView chipAll, chipBulk, chipCut, chipMaintain, chipVegan;
    private String activeFilter = "All";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diet, container, false);

        allPlans = DataProvider.getDietPlans();
        filteredPlans = new ArrayList<>(allPlans);

        initViews(view);
        setupRecyclerView();
        setupChips(view);

        return view;
    }

    private void initViews(View view) {
        rvDietPlans = view.findViewById(R.id.rvDietPlans);
        chipAll = view.findViewById(R.id.chipAll);
        chipBulk = view.findViewById(R.id.chipBulk);
        chipCut = view.findViewById(R.id.chipCut);
        chipMaintain = view.findViewById(R.id.chipMaintain);
        chipVegan = view.findViewById(R.id.chipVegan);
    }

    private void setupRecyclerView() {
        adapter = new DietPlanAdapter(filteredPlans, new DietPlanAdapter.OnPlanClickListener() {
            @Override
            public void onPlanClick(DietPlan plan) {
                Toast.makeText(getContext(), "📋 " + plan.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartPlan(DietPlan plan) {
                Toast.makeText(getContext(), "🚀 Starting " + plan.getName() + "!", Toast.LENGTH_SHORT).show();
            }
        });
        rvDietPlans.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDietPlans.setAdapter(adapter);
    }

    private void setupChips(View view) {
        View.OnClickListener chipListener = v -> {
            TextView chip = (TextView) v;
            String filter = chip.getText().toString();
            filterPlans(filter);
            updateChipStyles(chip);
        };

        chipAll.setOnClickListener(chipListener);
        chipBulk.setOnClickListener(chipListener);
        chipCut.setOnClickListener(chipListener);
        chipMaintain.setOnClickListener(chipListener);
        chipVegan.setOnClickListener(chipListener);
    }

    private void filterPlans(String filter) {
        filteredPlans.clear();
        for (DietPlan plan : allPlans) {
            if (filter.equals("All Plans") || plan.getCategory().contains(filter.toUpperCase())) {
                filteredPlans.add(plan);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void updateChipStyles(TextView activeChip) {
        TextView[] chips = {chipAll, chipBulk, chipCut, chipMaintain, chipVegan};
        for (TextView chip : chips) {
            chip.setBackgroundResource(R.drawable.bg_card_dark);
            chip.setTextColor(0xFF8888AA);
        }
        activeChip.setBackgroundResource(R.drawable.btn_primary);
        activeChip.setTextColor(0xFFFFFFFF);
    }
}
