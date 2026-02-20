package com.gymdiet.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gymdiet.app.R;
import com.gymdiet.app.models.Workout;
import com.gymdiet.app.utils.AnimUtils;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    private List<Workout> workouts;
    private OnWorkoutClickListener listener;

    public interface OnWorkoutClickListener {
        void onWorkoutClick(Workout workout);
    }

    public WorkoutAdapter(List<Workout> workouts, OnWorkoutClickListener listener) {
        this.workouts = workouts;
        this.listener = listener;
    }

    public void updateList(List<Workout> newList) {
        this.workouts = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_workout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout workout = workouts.get(position);

        holder.tvName.setText(workout.getName());
        holder.tvMuscle.setText(workout.getMuscleGroup());
        holder.tvEmoji.setText(workout.getEmoji());
        holder.tvSets.setText(workout.getSets() + " sets");
        holder.tvReps.setText(workout.getReps());
        holder.tvDifficulty.setText(workout.getDifficulty());

        // Color difficulty
        int color;
        switch (workout.getDifficulty()) {
            case "Beginner": color = 0xFF00D4AA; break;
            case "Advanced": color = 0xFFFF1744; break;
            default: color = 0xFFFFD600; break;
        }
        holder.tvDifficulty.setTextColor(color);

        // Entry animation with stagger
        holder.itemView.setAlpha(0f);
        holder.itemView.setTranslationX(40f);
        holder.itemView.animate()
                .alpha(1f)
                .translationX(0f)
                .setDuration(300)
                .setStartDelay(position * 60L)
                .start();

        holder.itemView.setOnClickListener(v -> {
            AnimUtils.pulse(v);
            if (listener != null) listener.onWorkoutClick(workout);
        });
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMuscle, tvEmoji, tvSets, tvReps, tvDifficulty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvWorkoutName);
            tvMuscle = itemView.findViewById(R.id.tvWorkoutMuscle);
            tvEmoji = itemView.findViewById(R.id.tvWorkoutEmoji);
            tvSets = itemView.findViewById(R.id.tvSets);
            tvReps = itemView.findViewById(R.id.tvReps);
            tvDifficulty = itemView.findViewById(R.id.tvDifficulty);
        }
    }
}
