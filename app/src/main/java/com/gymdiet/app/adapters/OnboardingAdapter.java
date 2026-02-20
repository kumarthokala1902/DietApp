package com.gymdiet.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gymdiet.app.R;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.ViewHolder> {

    private String[] titles;
    private String[] descriptions;
    private String[] emojis;

    public OnboardingAdapter(String[] titles, String[] descriptions, String[] emojis) {
        this.titles = titles;
        this.descriptions = descriptions;
        this.emojis = emojis;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_onboarding, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(titles[position]);
        holder.tvDescription.setText(descriptions[position]);
        holder.tvEmoji.setText(emojis[position]);

        // Animate on bind
        holder.tvEmoji.setScaleX(0f);
        holder.tvEmoji.setScaleY(0f);
        holder.tvEmoji.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(500)
                .setStartDelay(200)
                .start();

        holder.tvTitle.setAlpha(0f);
        holder.tvTitle.setTranslationY(30f);
        holder.tvTitle.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .setStartDelay(350)
                .start();

        holder.tvDescription.setAlpha(0f);
        holder.tvDescription.setTranslationY(30f);
        holder.tvDescription.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .setStartDelay(500)
                .start();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvEmoji;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvEmoji = itemView.findViewById(R.id.tvEmoji);
        }
    }
}
