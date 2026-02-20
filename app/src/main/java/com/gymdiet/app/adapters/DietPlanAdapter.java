package com.gymdiet.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gymdiet.app.R;
import com.gymdiet.app.models.DietPlan;
import com.gymdiet.app.utils.AnimUtils;

import java.util.List;

public class DietPlanAdapter extends RecyclerView.Adapter<DietPlanAdapter.ViewHolder> {

    private List<DietPlan> plans;
    private OnPlanClickListener listener;

    public interface OnPlanClickListener {
        void onPlanClick(DietPlan plan);
        void onStartPlan(DietPlan plan);
    }

    public DietPlanAdapter(List<DietPlan> plans, OnPlanClickListener listener) {
        this.plans = plans;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diet_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DietPlan plan = plans.get(position);

        holder.tvName.setText(plan.getName());
        holder.tvCategory.setText(plan.getCategory());
        holder.tvEmoji.setText(plan.getEmoji());
        holder.tvCalories.setText(String.format("%,d", plan.getCalories()));
        holder.tvMeals.setText(String.valueOf(plan.getMealsPerDay()));
        holder.tvDuration.setText(String.valueOf(plan.getDurationWeeks()));

        // Set gradient based on type
        int gradientRes;
        switch (plan.getGradientType()) {
            case 1: gradientRes = R.drawable.bg_gradient_green; break;
            case 2: gradientRes = R.drawable.bg_gradient_purple; break;
            default: gradientRes = R.drawable.bg_gradient_orange; break;
        }
        holder.viewGradient.setBackgroundResource(gradientRes);

        // Entry animation
        holder.itemView.setAlpha(0f);
        holder.itemView.setTranslationY(60f);
        holder.itemView.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(400)
                .setStartDelay(position * 100L)
                .start();

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onPlanClick(plan);
        });

        holder.btnStart.setOnClickListener(v -> {
            AnimUtils.pulse(v);
            if (listener != null) listener.onStartPlan(plan);
        });
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCategory, tvEmoji, tvCalories, tvMeals, tvDuration, btnStart;
        View viewGradient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvPlanName);
            tvCategory = itemView.findViewById(R.id.tvPlanCategory);
            tvEmoji = itemView.findViewById(R.id.tvPlanEmoji);
            tvCalories = itemView.findViewById(R.id.tvPlanCalories);
            tvMeals = itemView.findViewById(R.id.tvPlanMeals);
            tvDuration = itemView.findViewById(R.id.tvPlanDuration);
            btnStart = itemView.findViewById(R.id.btnStartPlan);
            viewGradient = itemView.findViewById(R.id.viewGradient);
        }
    }
}
