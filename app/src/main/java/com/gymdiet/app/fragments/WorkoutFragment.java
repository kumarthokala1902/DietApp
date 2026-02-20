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
import com.gymdiet.app.adapters.WorkoutAdapter;
import com.gymdiet.app.models.Workout;
import com.gymdiet.app.utils.AnimUtils;
import com.gymdiet.app.utils.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class WorkoutFragment extends Fragment {

    private RecyclerView rvWorkouts;
    private WorkoutAdapter adapter;
    private List<Workout> allWorkouts;
    private List<Workout> filteredWorkouts;

    private TextView chipFull, chipChest, chipBack, chipLegs, chipArms, chipCore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        allWorkouts = DataProvider.getWorkouts();
        filteredWorkouts = new ArrayList<>(allWorkouts);

        initViews(view);
        setupRecyclerView();
        setupChips();

        return view;
    }

    private void initViews(View view) {
        rvWorkouts = view.findViewById(R.id.rvWorkouts);
        chipFull = view.findViewById(R.id.chipFull);
        chipChest = view.findViewById(R.id.chipChest);
        chipBack = view.findViewById(R.id.chipBack);
        chipLegs = view.findViewById(R.id.chipLegs);
        chipArms = view.findViewById(R.id.chipArms);
        chipCore = view.findViewById(R.id.chipCore);
    }

    private void setupRecyclerView() {
        adapter = new WorkoutAdapter(filteredWorkouts, workout -> {
            AnimUtils.pulse(rvWorkouts);
            Toast.makeText(getContext(),
                    "🏋️ " + workout.getName() + " - " + workout.getSets() + " sets x " + workout.getReps(),
                    Toast.LENGTH_SHORT).show();
        });
        rvWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvWorkouts.setAdapter(adapter);
    }

    private void setupChips() {
        View.OnClickListener listener = v -> {
            TextView chip = (TextView) v;
            String filter = chip.getText().toString().replace("💪 ", "");
            filterWorkouts(filter);
            updateChipStyles(chip);
            AnimUtils.pulse(chip);
        };

        chipFull.setOnClickListener(listener);
        chipChest.setOnClickListener(listener);
        chipBack.setOnClickListener(listener);
        chipLegs.setOnClickListener(listener);
        chipArms.setOnClickListener(listener);
        chipCore.setOnClickListener(listener);
    }

    private void filterWorkouts(String filter) {
        filteredWorkouts.clear();
        for (Workout w : allWorkouts) {
            if (filter.equals("Full Body") || w.getCategory().equals(filter)) {
                filteredWorkouts.add(w);
            }
        }
        adapter.updateList(filteredWorkouts);
    }

    private void updateChipStyles(TextView activeChip) {
        TextView[] chips = {chipFull, chipChest, chipBack, chipLegs, chipArms, chipCore};
        for (TextView chip : chips) {
            chip.setBackgroundResource(R.drawable.bg_card_dark);
            chip.setTextColor(0xFF8888AA);
        }
        activeChip.setBackgroundResource(R.drawable.btn_primary);
        activeChip.setTextColor(0xFFFFFFFF);
    }
}
