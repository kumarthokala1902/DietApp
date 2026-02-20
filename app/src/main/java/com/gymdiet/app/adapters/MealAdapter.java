package com.gymdiet.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gymdiet.app.R;
import com.gymdiet.app.models.Meal;
import com.gymdiet.app.utils.AnimUtils;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    private List<Meal> meals;
    private OnMealClickListener listener;

    public interface OnMealClickListener {
        void onMealClick(Meal meal, int position);
    }

    public MealAdapter(List<Meal> meals, OnMealClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);

        holder.tvEmoji.setText(meal.getEmoji());
        holder.tvName.setText(meal.getName());
        holder.tvItems.setText(meal.getItems());
        holder.tvCalories.setText(String.valueOf(meal.getCalories()));
        holder.tvProtein.setText(meal.getProtein() + "g P");
        holder.tvCarbs.setText(meal.getCarbs() + "g C");
        holder.tvFat.setText(meal.getFats() + "g F");

        // Entry animation
        holder.itemView.setAlpha(0f);
        holder.itemView.setTranslationX(50f);
        holder.itemView.animate()
                .alpha(1f)
                .translationX(0f)
                .setDuration(350)
                .setStartDelay(position * 80L)
                .start();

        holder.itemView.setOnClickListener(v -> {
            AnimUtils.pulse(v);
            if (listener != null) listener.onMealClick(meal, position);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmoji, tvName, tvItems, tvCalories, tvProtein, tvCarbs, tvFat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmoji = itemView.findViewById(R.id.tvMealEmoji);
            tvName = itemView.findViewById(R.id.tvMealName);
            tvItems = itemView.findViewById(R.id.tvMealItems);
            tvCalories = itemView.findViewById(R.id.tvMealCalories);
            tvProtein = itemView.findViewById(R.id.tvMealProtein);
            tvCarbs = itemView.findViewById(R.id.tvMealCarbs);
            tvFat = itemView.findViewById(R.id.tvMealFat);
        }
    }
}
